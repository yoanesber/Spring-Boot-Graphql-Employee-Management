package com.yoanesber.graphql_employee_management.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;
import com.yoanesber.graphql_employee_management.dto.DepartmentInputDTO;
import com.yoanesber.graphql_employee_management.entity.Department;

/**
 * Mapper class for converting between Department and DepartmentDTO.
 * This class provides methods to convert a Department entity to a DTO and vice versa.
 */

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department d) {
        if (d == null) {
            return null;
        }

        return new DepartmentDTO(
                d.getId(),
                d.getDeptName(),
                d.getActive()
        );
    }

    public static DepartmentInputDTO toInputDTO(Department d) {
        if (d == null) {
            return null;
        }

        return new DepartmentInputDTO(
                d.getId(),
                d.getDeptName(),
                d.getActive(),
                d.getCreatedBy(),
                d.getUpdatedBy()
        );
    }

    public static List<DepartmentDTO> toDTOList(List<Department> dList) {
        if (dList == null || dList.isEmpty()) {
            return List.of();
        }

        return dList.stream()
                .map(DepartmentMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static Department toEntity(DepartmentDTO d) {
        if (d == null) {
            return null;
        }

        Department department = new Department();
        department.setId(d.getId());
        department.setDeptName(d.getDeptName());
        department.setActive(d.getActive());

        return department;
    }

    public static Department toEntity(DepartmentInputDTO d) {
        if (d == null) {
            return null;
        }

        Department department = new Department();
        department.setId(d.getId());
        department.setDeptName(d.getDeptName());
        department.setActive(d.getActive());
        department.setCreatedBy(d.getCreatedBy());
        department.setUpdatedBy(d.getUpdatedBy());

        return department;
    }

    public static List<Department> toEntityList(List<DepartmentDTO> dList) {
        if (dList == null || dList.isEmpty()) {
            return List.of();
        }

        return dList.stream()
                .map(DepartmentMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
