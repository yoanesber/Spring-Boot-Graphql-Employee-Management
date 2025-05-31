package com.yoanesber.graphql_employee_management.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoanesber.graphql_employee_management.entity.Department;
import com.yoanesber.graphql_employee_management.repository.DepartmentRepository;
import com.yoanesber.graphql_employee_management.service.DepartmentService;

/**
 * Service implementation for managing Department entities.
 * This class provides methods to save, retrieve, update, and delete departments.
 * It handles the business logic and interacts with the DepartmentRepository for data access.
 * The service ensures that departments are not duplicated and manages the active status of departments.
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Department saveDepartment(Department department) {
        Department existingDepartment = departmentRepository.findById(department.getId())
            .orElse(null);

        // If the department already exists, update it instead of creating a new one
        if (existingDepartment != null) {
            department.setUpdatedBy(department.getCreatedBy());
            return updateDepartment(department.getId(), department);
        } 

        department.setCreatedAt(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = departmentRepository
            .findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (departments.isEmpty()) {
            return List.of();
        }

        return departments;
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id)
            .orElse(null);
    }

    @Override
    @Transactional
    public Department updateDepartment(String id, Department department) {
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        if (existingDepartment == null) {
            return null;
        } 

        existingDepartment.setDeptName(department.getDeptName());
        existingDepartment.setActive(null != department.getActive() ? 
            department.getActive() : existingDepartment.getActive());
        existingDepartment.setUpdatedBy(department.getUpdatedBy());
        existingDepartment.setUpdatedAt(LocalDateTime.now());
        return departmentRepository.save(existingDepartment);
    }

    @Override
    @Transactional
    public Boolean deleteDepartment(String id) {
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        if (existingDepartment == null) {
            return false;
        }

        existingDepartment.setActive(false);
        existingDepartment.setDeleted(true);
        existingDepartment.setDeletedAt(LocalDateTime.now());
        departmentRepository.save(existingDepartment);
        
        return true;
    }

}
