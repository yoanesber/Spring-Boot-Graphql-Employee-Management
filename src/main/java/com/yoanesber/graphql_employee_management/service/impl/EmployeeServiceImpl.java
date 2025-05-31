package com.yoanesber.graphql_employee_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.entity.Department;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployeeId;
import com.yoanesber.graphql_employee_management.entity.Employee;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployee;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployeeId;
import com.yoanesber.graphql_employee_management.entity.TitleEmployee;
import com.yoanesber.graphql_employee_management.entity.TitleEmployeeId;
import com.yoanesber.graphql_employee_management.repository.EmployeeRepository;
import com.yoanesber.graphql_employee_management.service.DepartmentService;
import com.yoanesber.graphql_employee_management.service.EmployeeService;

/**
 * Service implementation for managing Employee entities.
 * This class provides methods to save, retrieve, update, and delete employees.
 * It handles the business logic and interacts with the EmployeeRepository for data access.
 * The service ensures that employees are properly linked to their departments, salaries, and titles.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(DepartmentService departmentService, 
        EmployeeRepository employeeRepository) {
        this.departmentService = departmentService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        // Check if the employee with first name, last name, and birth date already exists
        if (isEmployeeExists(employee)) {
            throw new EntityExistsException("Employee already exists with name: " + 
                employee.getFirstName() + " " + employee.getLastName() + 
                " and birth date: " + employee.getBirthDate() +
                " and status: " + (employee.getActive() ? "active" : "inactive"));
        }

        // prepare the employee
        employee.setCreatedAt(LocalDateTime.now());

        // Prepare & save the departments
        List<DepartmentEmployee> departmentEmployees = new ArrayList<>();
        employee.getDepartments().forEach(department -> {
            // Get the department entity
            Department deptEntity = departmentService
                .getDepartmentById(department.getId().getDepartmentId());

            if (deptEntity == null) {
                throw new EntityNotFoundException("Department not found: " + 
                    department.getId().getDepartmentId());
            }

            // Set the department employee
            DepartmentEmployee deptEmp = new DepartmentEmployee();
            deptEmp.setEmployee(employee);
            deptEmp.setDepartment(deptEntity);
            deptEmp.setFromDate(department.getFromDate());
            deptEmp.setToDate(department.getToDate());
            deptEmp.setId(new DepartmentEmployeeId());
            deptEmp.getId().setDepartmentId(deptEntity.getId());
            departmentEmployees.add(deptEmp);

            // Clear the employee reference in the department
            department.setEmployee(null);
        });
        employee.getDepartments().clear();
        employee.setDepartments(departmentEmployees);
        
        // Prepare & save the salaries
        List<SalaryEmployee> salaryEmployees = new ArrayList<>();
        employee.getSalaries().forEach(salary -> {
            // Set the salary employee
            SalaryEmployee salEmp = new SalaryEmployee();
            salEmp.setEmployee(employee);
            salEmp.setAmount(salary.getAmount());
            salEmp.setToDate(salary.getToDate());
            salEmp.setId(new SalaryEmployeeId());
            salEmp.getId().setFromDate(salary.getId().getFromDate());
            salaryEmployees.add(salEmp);

            // Clear the employee reference in the salary
            salary.setEmployee(null);
        });
        employee.getSalaries().clear();
        employee.setSalaries(salaryEmployees);
        
        // Prepare & save the titles
        List<TitleEmployee> titleEmployees = new ArrayList<>();
        employee.getTitles().forEach(title -> {
            // Set the title employee
            TitleEmployee titEmp = new TitleEmployee();
            titEmp.setEmployee(employee);
            titEmp.setToDate(title.getToDate());
            titEmp.setId(new TitleEmployeeId());
            titEmp.getId().setTitle(title.getId().getTitle());
            titEmp.getId().setFromDate(title.getId().getFromDate());
            titleEmployees.add(titEmp);

            // Clear the employee reference in the title
            title.setEmployee(null);
        });
        employee.getTitles().clear();
        employee.setTitles(titleEmployees);

        // Save the employee to get the ID
        return employeeRepository.save(employee);
    }

    private Boolean isEmployeeExists(Employee employee) {
        return employeeRepository.existsByFirstNameAndLastNameAndBirthDate(
            employee.getFirstName(), 
            employee.getLastName(), 
            employee.getBirthDate());
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository
            .findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (employees.isEmpty()) {
            return List.of();
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElse(null);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
            .orElse(null);
        
        if (existingEmployee == null) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        } 

        // Update the employee fields
        existingEmployee.setBirthDate(employee.getBirthDate());
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setHireDate(employee.getHireDate());
        existingEmployee.setActive(null != employee.getActive() ? 
            employee.getActive() : existingEmployee.getActive());
        existingEmployee.setUpdatedBy(employee.getUpdatedBy());
        existingEmployee.setUpdatedAt(LocalDateTime.now());

        // Sync departments
        if (employee.getDepartments() != null) {
            syncDepartmentEmployees(existingEmployee, employee.getDepartments());
        }

        // Sync salaries
        if (employee.getSalaries() != null) {
            syncSalaryEmployees(existingEmployee, employee.getSalaries());
        }

        // Sync titles
        if (employee.getTitles() != null) {
            syncTitleEmployees(existingEmployee, employee.getTitles());
        }

        // Save the employee
        return employeeRepository.save(existingEmployee);
    }

    private void syncDepartmentEmployees(Employee existingEmployee, List<DepartmentEmployee> newDepartments) {
        Map<DepartmentEmployeeId, DepartmentEmployee> existingMap = existingEmployee.getDepartments().stream()
            .collect(Collectors.toMap(DepartmentEmployee::getId, d -> d));

        Map<DepartmentEmployeeId, DepartmentEmployee> inputMap = newDepartments.stream()
            .map(d -> {
                // Ensure the ID is set correctly
                if (d.getId() == null) {
                    d.setId(new DepartmentEmployeeId());
                }
                d.getId().setEmployeeId(existingEmployee.getId());
                return d;
            })
            .collect(Collectors.toMap(DepartmentEmployee::getId, d -> d));

        // Remove departments that are not in the new list
        existingMap.keySet().stream()
            .filter(id -> {
                // Check if the department is not in the input map
                boolean notInInput = !inputMap.containsKey(id);
                return notInInput;
            })
            .forEach(id -> {
                DepartmentEmployee toRemove = existingMap.get(id);
                existingEmployee.getDepartments().remove(toRemove);
            });

        // Add or update departments
        for (DepartmentEmployeeId inputId : inputMap.keySet()) {
            DepartmentEmployee inputData = inputMap.get(inputId);
            if (!existingMap.containsKey(inputId)) {
                // Get the department entity
                Department deptEntity = departmentService
                    .getDepartmentById(inputData.getId().getDepartmentId());

                if (deptEntity == null) {
                    throw new EntityNotFoundException("Department not found: " + 
                        inputData.getId().getDepartmentId());
                }

                // Not found in existing, add new department
                inputData.setEmployee(existingEmployee);
                inputData.setDepartment(deptEntity);
                inputData.setId(new DepartmentEmployeeId(existingEmployee.getId(), 
                    deptEntity.getId()));
                existingEmployee.getDepartments().add(inputData);
            } else {
                // Found in existing, update the toDate
                DepartmentEmployee existing = existingMap.get(inputId);
                existing.setFromDate(inputData.getFromDate());
                existing.setToDate(inputData.getToDate());
            }
        }
    }

    private void syncSalaryEmployees(Employee existingEmployee, List<SalaryEmployee> newSalaries) {
        Map<SalaryEmployeeId, SalaryEmployee> existingMap = existingEmployee.getSalaries().stream()
            .collect(Collectors.toMap(SalaryEmployee::getId, s -> s));

        Map<SalaryEmployeeId, SalaryEmployee> inputMap = newSalaries.stream()
            .map(s -> {
                // Ensure the ID is set correctly
                if (s.getId() == null) {
                    s.setId(new SalaryEmployeeId());
                }
                s.getId().setEmployeeId(existingEmployee.getId());
                return s;
            })
            .collect(Collectors.toMap(SalaryEmployee::getId, s -> s));

        // Remove salaries that are not in the new list
        existingMap.keySet().stream()
            .filter(id -> !inputMap.containsKey(id))
            .forEach(id -> {
                SalaryEmployee toRemove = existingMap.get(id);
                existingEmployee.getSalaries().remove(toRemove);
            });

        // Add or update salaries
        for (SalaryEmployeeId inputId : inputMap.keySet()) {
            SalaryEmployee inputData = inputMap.get(inputId);
            if (!existingMap.containsKey(inputId)) {
                // Not found in existing, add new salary
                inputData.setId(new SalaryEmployeeId(existingEmployee.getId(), 
                    inputData.getId().getFromDate()));
                inputData.setEmployee(existingEmployee);
                existingEmployee.getSalaries().add(inputData);
            } else {
                // Found in existing, update the toDate
                SalaryEmployee existing = existingMap.get(inputId);
                existing.setAmount(inputData.getAmount());
                existing.setToDate(inputData.getToDate());
            }
        }
    }

    private void syncTitleEmployees(Employee existingEmployee, List<TitleEmployee> newTitles) {
        Map<TitleEmployeeId, TitleEmployee> existingMap = existingEmployee.getTitles().stream()
            .collect(Collectors.toMap(TitleEmployee::getId, t -> t));

        Map<TitleEmployeeId, TitleEmployee> inputMap = newTitles.stream()
            .map(t -> {
                // Ensure the ID is set correctly
                if (t.getId() == null) {
                    t.setId(new TitleEmployeeId());
                }
                t.getId().setEmployeeId(existingEmployee.getId());
                return t;
            })
            .collect(Collectors.toMap(TitleEmployee::getId, t -> t));

        // Remove titles that are not in the new list
        existingMap.keySet().stream()
            .filter(id -> !inputMap.containsKey(id))
            .forEach(id -> {
                TitleEmployee toRemove = existingMap.get(id);
                existingEmployee.getTitles().remove(toRemove);
            });

        // Add or update titles
        for (TitleEmployeeId inputId : inputMap.keySet()) {
            TitleEmployee inputData = inputMap.get(inputId);
            if (!existingMap.containsKey(inputId)) {
                // Not found in existing, add new title
                inputData.setId(new TitleEmployeeId(existingEmployee.getId(), 
                    inputData.getId().getTitle(), inputData.getId().getFromDate()));
                inputData.setEmployee(existingEmployee);
                existingEmployee.getTitles().add(inputData);
            } else {
                // Found in existing, update the toDate
                TitleEmployee existing = existingMap.get(inputId);
                existing.setToDate(inputData.getToDate());
            }
        }
    }

    
    @Override
    @Transactional
    public Boolean deleteEmployee(Long id) {
        Assert.notNull(id, "Employee id cannot be null");

        Employee existingEmployee = employeeRepository.findById(id)
            .orElse(null);

        if (existingEmployee == null) {
            return false;
        }

        existingEmployee.setActive(false);
        existingEmployee.setDeleted(true);
        existingEmployee.setDeletedAt(LocalDateTime.now());
        employeeRepository.save(existingEmployee);

        return true;
    }
}
