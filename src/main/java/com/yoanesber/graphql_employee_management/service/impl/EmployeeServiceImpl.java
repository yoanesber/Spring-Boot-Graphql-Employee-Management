package com.yoanesber.graphql_employee_management.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.dto.EmployeeCreateDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeUpdateDTO;
import com.yoanesber.graphql_employee_management.entity.Department;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;
import com.yoanesber.graphql_employee_management.entity.Employee;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployee;
import com.yoanesber.graphql_employee_management.entity.TitleEmployee;
import com.yoanesber.graphql_employee_management.repository.EmployeeRepository;
import com.yoanesber.graphql_employee_management.service.DepartmentService;
import com.yoanesber.graphql_employee_management.service.DepartmentEmployeeService;
import com.yoanesber.graphql_employee_management.service.EmployeeService;
import com.yoanesber.graphql_employee_management.service.SalaryEmployeeService;
import com.yoanesber.graphql_employee_management.service.TitleEmployeeService;

/*
 * EmployeeServiceImpl is an implementation of the EmployeeService interface.
 * It provides methods to manage employee records in the database.
 * The class is annotated with @Service, indicating that it's a service layer component in the Spring context.
 * It uses the EmployeeRepository to perform CRUD operations on employee records.
 * The saveEmployee method is annotated with @Transactional, ensuring that the operation is performed within a transaction.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentService departmentService;

    private final DepartmentEmployeeService departmentEmployeeService;

    private final SalaryEmployeeService salaryEmployeeService;

    private final TitleEmployeeService titleEmployeeService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
        DepartmentService departmentService, DepartmentEmployeeService departmentEmployeeService,
        SalaryEmployeeService salaryEmployeeService, TitleEmployeeService titleEmployeeService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.departmentEmployeeService = departmentEmployeeService;
        this.salaryEmployeeService = salaryEmployeeService;
        this.titleEmployeeService = titleEmployeeService;
    }

    @Override
    @Transactional
    public EmployeeDTO saveEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Assert.notNull(employeeCreateDTO, "Employee cannot be null");

        // Prepare the employee
        Employee employee = new Employee();
        employee.setBirthDate(employeeCreateDTO.getBirthDate());
        employee.setFirstName(employeeCreateDTO.getFirstName());
        employee.setLastName(employeeCreateDTO.getLastName());
        employee.setGender(employeeCreateDTO.getGender());
        employee.setHireDate(employeeCreateDTO.getHireDate());
        employee.setActiveStatus(null != employeeCreateDTO.getActiveStatus() ? employeeCreateDTO.getActiveStatus() : true);
        employee.setCreatedBy((Long)employeeCreateDTO.getCreatedBy());
        employee.setCreatedDate(OffsetDateTime.now());
        employee.setUpdatedBy((Long)employeeCreateDTO.getCreatedBy());
        employee.setUpdatedDate(OffsetDateTime.now());
        
        // Save the employee to get the ID
        Employee savedEmployee = employeeRepository.save(employee);
        
        // Prepare & save the departments
        employeeCreateDTO.getDepartments().forEach(department -> {
            // Get the department entity
            Department deptEntity = new Department(departmentService
                .getDepartmentById(department.getDepartmentId()));

            // Create the department employee
            DepartmentEmployee departmentEmployee = new DepartmentEmployee(savedEmployee, deptEntity);
            
            // Set the from and to dates
            departmentEmployee.setFromDate(department.getFromDate());
            departmentEmployee.setToDate(department.getToDate());

            // Save the department employee
            departmentEmployeeService.saveDepartmentEmployee(departmentEmployee);
        });
        
        // Prepare & save the salaries
        employeeCreateDTO.getSalaries().forEach(salary -> {
            // Create the salary employee
            SalaryEmployee salaryEmployee = new SalaryEmployee(savedEmployee, salary.getFromDate());
            
            // Set the amount and to date
            salaryEmployee.setAmount((Long)salary.getAmount());
            salaryEmployee.setToDate(salary.getToDate());

            // Save the salary employee
            salaryEmployeeService.saveSalaryEmployee(salaryEmployee);
        });
        
        // Prepare & save the titles
        employeeCreateDTO.getTitles().forEach(title -> {
            // Create the title employee
            TitleEmployee titleEmployee = new TitleEmployee(savedEmployee, title.getTitle(), title.getFromDate());
            
            // Set the to date
            titleEmployee.setToDate(title.getToDate());

            // Save the title employee
            titleEmployeeService.saveTitleEmployee(titleEmployee);
        });

        return new EmployeeDTO(savedEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        // Get all employees sorted by id in ascending order
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // Check if the list is empty
        if (employees == null || employees.isEmpty()) {
            return List.of();
        }

        // Return the list of employees
        return employees.stream().map(EmployeeDTO::new).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Assert.notNull(id, "Employee id cannot be null");

        // Get the employee by id
        Employee employee = employeeRepository.findById(id)
            .orElse(null);

        // Check if the employee exists
        if (employee == null) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist");
        }

        // Return the employee
        return new EmployeeDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO) {
        Assert.notNull(id, "Employee id cannot be null");
        Assert.notNull(employeeUpdateDTO, "Employee cannot be null");

        // Get the existing employee
        Employee existingEmployee = employeeRepository.findById(id)
            .orElse(null);
        
        // Check if the employee exists
        if (existingEmployee == null) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist");
        } 
        
        // Update the employee
        existingEmployee.setBirthDate(employeeUpdateDTO.getBirthDate());
        existingEmployee.setFirstName(employeeUpdateDTO.getFirstName());
        existingEmployee.setLastName(employeeUpdateDTO.getLastName());
        existingEmployee.setGender(employeeUpdateDTO.getGender());
        existingEmployee.setHireDate(employeeUpdateDTO.getHireDate());
        existingEmployee.setActiveStatus(null != employeeUpdateDTO.getActiveStatus() ? employeeUpdateDTO.getActiveStatus() : existingEmployee.getActiveStatus());
        existingEmployee.setUpdatedBy((Long)employeeUpdateDTO.getUpdatedBy());
        existingEmployee.setUpdatedDate(OffsetDateTime.now());

        // Remove all childs associated with the employee
        existingEmployee.getDepartments().clear();
        existingEmployee.getSalaries().clear();
        existingEmployee.getTitles().clear();

        // Prepare the departments
        employeeUpdateDTO.getDepartments().forEach(department -> {
            // Get the department entity
            Department deptEntity = new Department(departmentService
                .getDepartmentById(department.getDepartmentId()));

            // Create the department employee
            DepartmentEmployee departmentEmployee = new DepartmentEmployee(existingEmployee, deptEntity);
            
            // Set the from and to dates
            departmentEmployee.setFromDate(department.getFromDate());
            departmentEmployee.setToDate(department.getToDate());

            // Add the department employee to the list
            existingEmployee.getDepartments().add(departmentEmployee);
        });

        // Prepare the salaries
        employeeUpdateDTO.getSalaries().forEach(salary -> {
            // Create the salary employee
            SalaryEmployee salaryEmployee = new SalaryEmployee(existingEmployee, salary.getFromDate());

            // Set the amount and to date
            salaryEmployee.setAmount((Long)salary.getAmount());
            salaryEmployee.setToDate(salary.getToDate());

            // Add the salary employee to the list
            existingEmployee.getSalaries().add(salaryEmployee);
        });

        // Prepare the titles
        employeeUpdateDTO.getTitles().forEach(title -> {
            // Create the title employee
            TitleEmployee titleEmployee = new TitleEmployee(existingEmployee, title.getTitle(), title.getFromDate());

            // Set the to date
            titleEmployee.setToDate(title.getToDate());

            // Add the title employee to the list
            existingEmployee.getTitles().add(titleEmployee);
        });

        // Save & return the employee
        return new EmployeeDTO(employeeRepository.save(existingEmployee));
    }
    
    @Override
    @Transactional
    public Boolean deleteEmployee(Long id) {
        Assert.notNull(id, "Employee id cannot be null");

        // Get the employee by id
        Employee employee = employeeRepository.findById(id)
            .orElse(null);

        // Check if the employee exists
        if (employee == null) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist");
        }

        // Delete the employee
        employeeRepository.deleteById(id);

        // Return true
        return true;
    }
}
