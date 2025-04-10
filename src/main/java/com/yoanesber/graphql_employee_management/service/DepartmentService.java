package com.yoanesber.graphql_employee_management.service;

import java.util.List;

import com.yoanesber.graphql_employee_management.dto.DepartmentCreateDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentUpdateDTO;

public interface DepartmentService {
    // Save department
    DepartmentDTO saveDepartment(DepartmentCreateDTO departmentCreateDTO);

    // Get all departments
    List<DepartmentDTO> getAllDepartments();

    // Get department by id
    DepartmentDTO getDepartmentById(String id);

    // Update department
    DepartmentDTO updateDepartment(String id, DepartmentUpdateDTO departmentUpdateDTO);

    // Delete department
    Boolean deleteDepartment(String id);
}
