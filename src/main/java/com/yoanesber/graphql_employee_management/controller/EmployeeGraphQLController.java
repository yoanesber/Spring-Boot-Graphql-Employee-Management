package com.yoanesber.graphql_employee_management.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import com.yoanesber.graphql_employee_management.dto.EmployeeInputDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeDTO;
import com.yoanesber.graphql_employee_management.mapper.EmployeeMapper;
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

    public EmployeeGraphQLController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @MutationMapping
    public EmployeeDTO saveEmployee(@Valid @Argument EmployeeInputDTO input) {
        if (input == null) {
            throw new IllegalArgumentException("Employee body request cannot be null");
        }

        try {
            EmployeeDTO createdEmployee = EmployeeMapper.toDTO(
                employeeService.saveEmployee(
                    EmployeeMapper.toEntity(input)
                )
            );
            
            if (createdEmployee == null) {
                throw new RuntimeException("Failed to create employee");
            }

            return createdEmployee;
        } catch (EntityExistsException e) {
            throw new RuntimeException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public List<EmployeeDTO> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = EmployeeMapper.toDTOList(
                employeeService.getAllEmployees()
            );

            if (employees == null || employees.isEmpty()) {
                throw new RuntimeException("No employees found");
            }

            return employees;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public EmployeeDTO getEmployeeById(@Argument Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee id cannot be null");
        } 

        try {
            EmployeeDTO employee = EmployeeMapper.toDTO(
                employeeService.getEmployeeById(id)
            );

            if (employee == null) {
                throw new RuntimeException("Employee not found with id: " + id);
            }
            
            return employee;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public EmployeeDTO updateEmployee(@Argument Long id, @Valid @Argument EmployeeInputDTO input) {
        if (id == null) {
            throw new IllegalArgumentException("Employee id cannot be null");
        }

        if (input == null) {
            throw new IllegalArgumentException("Employee body request cannot be null");
        }

        try {
            EmployeeDTO updatedEmployee = EmployeeMapper.toDTO(
                employeeService.updateEmployee(id, EmployeeMapper.toEntity(input))
            );

            if (updatedEmployee == null) {
                throw new RuntimeException("Failed to update employee with id: " + id);
            }

            return updatedEmployee;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public Boolean deleteEmployee(@Argument Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee id cannot be null");
        }

        try {
            if (!employeeService.deleteEmployee(id)) {
                throw new RuntimeException("Failed to delete employee with id: " + id);
            }
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
