package com.yoanesber.graphql_employee_management.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentInputDTO;
import com.yoanesber.graphql_employee_management.mapper.DepartmentMapper;
import com.yoanesber.graphql_employee_management.service.DepartmentService;

/**
 * This class is responsible for handling GraphQL requests related to Department entities.
 * It uses Spring GraphQL to map GraphQL queries and mutations to Java methods.
 * The methods are annotated with @QueryMapping and @MutationMapping to indicate their purpose.
 * The input DTOs are validated using Jakarta Bean Validation (JSR 380).
 */

@Controller
@Validated // Validate the input DTOs using Jakarta Bean Validation (JSR 380)
public class DepartmentGraphQLController {
    private final DepartmentService departmentService;

    public DepartmentGraphQLController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @MutationMapping
    public DepartmentDTO saveDepartment(@Valid @Argument DepartmentInputDTO input) {
        // Check if the input is null
        if (input == null) {
            throw new IllegalArgumentException("Department body request cannot be null");
        } 

        if (input.getDeptName() == null || input.getDeptName().isBlank()) {
            throw new IllegalArgumentException("Department name cannot be blank");
        }

        try {
            DepartmentDTO createdDepartment = DepartmentMapper.toDTO(
                departmentService.saveDepartment(
                    DepartmentMapper.toEntity(input)
                )
            );

            if (createdDepartment == null) {
                throw new RuntimeException("Failed to create department");
            }

            return createdDepartment;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public List<DepartmentDTO> getAllDepartments() {
        try {
            List<DepartmentDTO> departments = DepartmentMapper.toDTOList(
                departmentService.getAllDepartments()
            );

            if (departments.isEmpty()) {
                throw new RuntimeException("No departments found");
            }

            return departments;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public DepartmentDTO getDepartmentById(@Argument String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Department id cannot be null or empty");
        }

        try {
            id = id.toLowerCase();
            DepartmentDTO department = DepartmentMapper.toDTO(
                departmentService.getDepartmentById(id)
            );

            if (department == null) {
                throw new RuntimeException("Department not found with id: " + id);
            }

            return department;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public DepartmentDTO updateDepartment(@Argument String id, @Valid @Argument DepartmentInputDTO input) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Department id request cannot be null");
        }

        if (input == null) {
            throw new IllegalArgumentException("Department body request cannot be null");
        }

        try {
            id = id.toLowerCase();
            DepartmentDTO updatedDepartment = DepartmentMapper.toDTO(
                departmentService.updateDepartment(id, DepartmentMapper.toEntity(input))
            );

            if (updatedDepartment == null) {
                throw new RuntimeException("Department not found with id: " + id);
            }

            return updatedDepartment;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public Boolean deleteDepartment(@Argument String id) {
        // Check if the input is null
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Department id cannot be blank");
        }
        
        try {
            
            // Delete department
            id = id.toLowerCase();
            if (!departmentService.deleteDepartment(id)) {
                throw new RuntimeException("Department not found with id: " + id);
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
