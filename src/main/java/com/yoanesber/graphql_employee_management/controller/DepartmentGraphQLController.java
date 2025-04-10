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

import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentCreateDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentUpdateDTO;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DepartmentGraphQLController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @MutationMapping
    public DepartmentDTO saveDepartment(@Valid @Argument DepartmentCreateDTO departmentCreateDTO) {
        // Check if the input is null
        if (departmentCreateDTO == null) {
            logger.error("DepartmentCreateDTO is null");
            throw new IllegalArgumentException("DepartmentCreateDTO cannot be null");
        } 

        try {
            // Save and return department
            return departmentService.saveDepartment(departmentCreateDTO);
        } catch (Exception e) {
            logger.error("Error saving department: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public List<DepartmentDTO> getAllDepartments() {
        try {
            // Get all departments
            return departmentService.getAllDepartments();
        } catch (Exception e) {
            logger.error("Error fetching all departments: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @QueryMapping
    public DepartmentDTO getDepartmentById(@Argument String id) {
        // Check if the input is null
        if (id.isBlank()) {
            logger.error("Department ID is blank");
            throw new IllegalArgumentException("Department ID cannot be blank");
        }

        try {
            // Get department by id
            id = id.toLowerCase();
            return departmentService.getDepartmentById(id);
        } catch (Exception e) {
            logger.error("Error fetching department by id: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public DepartmentDTO updateDepartment(@Argument String id, @Valid @Argument DepartmentUpdateDTO departmentUpdateDTO) {
        // Check if the input is null
        if (id.isBlank() || departmentUpdateDTO == null) {
            logger.error("Department ID or DepartmentUpdateDTO is null");
            throw new IllegalArgumentException("Department ID and DepartmentUpdateDTO cannot be null");
        }

        try {
            // Update department
            id = id.toLowerCase();
            return departmentService.updateDepartment(id, departmentUpdateDTO);
        } catch (Exception e) {
            logger.error("Error updating department: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping
    public Boolean deleteDepartment(@Argument String id) {
        // Check if the input is null
        if (id.isBlank()) {
            logger.error("Department ID is blank");
            throw new IllegalArgumentException("Department ID cannot be blank");
        }
        
        try {
            
            // Delete department
            id = id.toLowerCase();
            return departmentService.deleteDepartment(id);
        } catch (Exception e) {
            logger.error("Error deleting department: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
