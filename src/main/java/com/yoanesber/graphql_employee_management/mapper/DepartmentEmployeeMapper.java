package com.yoanesber.graphql_employee_management.mapper;

import com.yoanesber.graphql_employee_management.dto.DepartmentEmployeeDTO;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployeeId;

/**
 * Mapper class for converting between DepartmentEmployee and DepartmentEmployeeDTO.
 * This class provides methods to convert a DepartmentEmployee entity to a DTO and vice versa.
 */

public class DepartmentEmployeeMapper {
    public static DepartmentEmployeeDTO toDTO(DepartmentEmployee de) {
        if (de == null) {
            return null;
        }

        return new DepartmentEmployeeDTO(
                de.getId().getDepartmentId(),
                de.getFromDate(),
                de.getToDate()
        );
    }

    public static DepartmentEmployee toEntity(DepartmentEmployeeDTO de) {
        if (de == null) {
            return null;
        }

        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        departmentEmployee.setId(new DepartmentEmployeeId(
                null,
                de.getDepartmentId()
        ));
        departmentEmployee.setFromDate(de.getFromDate());
        departmentEmployee.setToDate(de.getToDate());

        return departmentEmployee;
    }
}
