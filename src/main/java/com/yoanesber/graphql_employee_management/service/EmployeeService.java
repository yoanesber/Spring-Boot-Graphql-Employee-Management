package com.yoanesber.graphql_employee_management.service;

import java.util.List;

import com.yoanesber.graphql_employee_management.dto.EmployeeCreateDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeUpdateDTO;

public interface EmployeeService {
    // Save employee
    EmployeeDTO saveEmployee(EmployeeCreateDTO employeeCreateDTO);

    // Get all employees
    List<EmployeeDTO> getAllEmployees();

    // Get employee by id
    EmployeeDTO getEmployeeById(Long id);

    // Update employee
    EmployeeDTO updateEmployee(Long id, EmployeeUpdateDTO employeeUpdateDTO);

    // Delete employee
    Boolean deleteEmployee(Long id);
}
