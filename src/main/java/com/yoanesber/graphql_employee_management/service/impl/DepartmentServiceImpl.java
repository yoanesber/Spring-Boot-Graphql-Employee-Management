package com.yoanesber.graphql_employee_management.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentCreateDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentUpdateDTO;
import com.yoanesber.graphql_employee_management.entity.Department;
import com.yoanesber.graphql_employee_management.repository.DepartmentRepository;
import com.yoanesber.graphql_employee_management.service.DepartmentService;

/**
 * DepartmentServiceImpl is an implementation of the DepartmentService interface.
 * It provides methods to manage department records in the database.
 * The class is annotated with @Service, indicating that it's a service layer component in the Spring context.
 * It uses the DepartmentRepository to perform CRUD operations on department records.
 * The saveDepartment method is annotated with @Transactional, ensuring that the operation is performed within a transaction.
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public DepartmentDTO saveDepartment(DepartmentCreateDTO departmentCreateDTO) {
        Assert.notNull(departmentCreateDTO, "Department cannot be null");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(departmentCreateDTO.getId())
            .orElse(null);

        // Check if department exists
        if (existingDepartment != null) {
            throw new IllegalArgumentException("Department with id " + departmentCreateDTO.getId() + " already exists");
        } 
        
        // Prepare department entity
        Department department = new Department();
        department.setId(departmentCreateDTO.getId().toLowerCase());
        department.setDeptName(departmentCreateDTO.getDeptName());
        department.setActive(null != departmentCreateDTO.getActive() ? departmentCreateDTO.getActive() : true);
        department.setCreatedBy((Long)departmentCreateDTO.getCreatedBy());
        department.setCreatedDate(OffsetDateTime.now());
        department.setUpdatedBy((Long)departmentCreateDTO.getCreatedBy());
        department.setUpdatedDate(OffsetDateTime.now());

        // Save and return department
        return new DepartmentDTO(departmentRepository.save(department));
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        // Get all departments sorted by id in ascending order
        List<Department> departments = departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // Check if departments exist
        if (departments == null || departments.isEmpty()) {
            return List.of();
        }

        // Return departments
        return departments.stream().map(DepartmentDTO::new).toList();
    }

    @Override
    public DepartmentDTO getDepartmentById(String id) {
        Assert.hasText(id, "Department id cannot be null or empty");

        // Get department by id
        Department department = departmentRepository.findById(id)
            .orElse(null);

        // Check if department exists
        if (department == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        }

        // Return department
        return new DepartmentDTO(department);
    }

    @Override
    @Transactional
    public DepartmentDTO updateDepartment(String id, DepartmentUpdateDTO departmentUpdateDTO) {
        Assert.hasText(id, "Department id cannot be null or empty");
        Assert.notNull(departmentUpdateDTO, "Department cannot be null");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        // Check if department exists
        if (existingDepartment == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        } 
        
        // Update department
        existingDepartment.setDeptName(departmentUpdateDTO.getDeptName());
        existingDepartment.setActive(null != departmentUpdateDTO.getActive() ? departmentUpdateDTO.getActive() : existingDepartment.getActive());
        existingDepartment.setUpdatedBy((Long)departmentUpdateDTO.getUpdatedBy());
        existingDepartment.setUpdatedDate(OffsetDateTime.now());

        // Save department
        return new DepartmentDTO(departmentRepository.save(existingDepartment));
    }

    @Override
    @Transactional
    public Boolean deleteDepartment(String id) {
        Assert.hasText(id, "Department id cannot be null or empty");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        // Check if department exists
        if (existingDepartment == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        }

        // Delete department
        departmentRepository.deleteById(id);

        return true;
    }
}
