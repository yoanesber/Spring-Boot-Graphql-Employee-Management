package com.yoanesber.graphql_employee_management.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import com.yoanesber.graphql_employee_management.dto.EmployeeCreateDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeUpdateDTO;
import com.yoanesber.graphql_employee_management.service.EmployeeService;

/**
 * This class is responsible for handling GraphQL requests related to Employee entities.
 * It uses Spring GraphQL to map GraphQL queries and mutations to Java methods.
 * The methods are annotated with @QueryMapping and @MutationMapping to indicate their purpose.
 * The input DTOs are validated using Jakarta Bean Validation (JSR 380).
 */

@Controller
@Validated // Validate the input DTOs using Jakarta Bean Validation (JSR 380)
public class EmployeeGraphQLController {
    private final EmployeeService employeeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EmployeeGraphQLController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @MutationMapping
    public EmployeeDTO saveEmployee(@Valid @Argument EmployeeCreateDTO employeeCreateDTO) {
        // Check if the input is null
        if (employeeCreateDTO == null) {
            logger.error("EmployeeCreateDTO is null");
            throw new IllegalArgumentException("EmployeeCreateDTO cannot be null");
        }

        try {
            // Save and return employee
            return employeeService.saveEmployee(employeeCreateDTO);
        } catch (Exception e) {
            logger.error("Error saving employee: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public List<EmployeeDTO> getAllEmployees() {
        try {
            // Get all employees
            return employeeService.getAllEmployees();
        } catch (Exception e) {
            logger.error("Error fetching all employees: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public EmployeeDTO getEmployeeById(@Argument Long id) {
        // Check if the id is null
        if (id == null) {
            logger.error("Employee ID is null");
            throw new IllegalArgumentException("Employee ID cannot be null");
        } 

        try {
            // Get employee by id
            return employeeService.getEmployeeById(id);
        } catch (Exception e) {
            logger.error("Error fetching employee by id: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public EmployeeDTO updateEmployee(@Argument Long id, @Valid @Argument EmployeeUpdateDTO employeeUpdateDTO) {
        // Check if the id and employee are null
        if (id == null || employeeUpdateDTO == null) {
            logger.error("Employee ID or EmployeeUpdateDTO is null");
            throw new IllegalArgumentException("Employee ID and EmployeeUpdateDTO cannot be null");
        }

        try {
            // Update employee
            return employeeService.updateEmployee(id, employeeUpdateDTO);
        } catch (Exception e) {
            logger.error("Error updating employee: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public Boolean deleteEmployee(@Argument Long id) {
        // Check if the id is null
        if (id == null) {
            logger.error("Employee ID is null");
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        try {
            // Delete employee
            return employeeService.deleteEmployee(id);
        } catch (Exception e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
