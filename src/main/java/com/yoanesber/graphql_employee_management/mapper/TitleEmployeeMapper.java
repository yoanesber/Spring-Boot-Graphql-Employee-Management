package com.yoanesber.graphql_employee_management.mapper;

import com.yoanesber.graphql_employee_management.dto.TitleEmployeeDTO;
import com.yoanesber.graphql_employee_management.entity.TitleEmployee;
import com.yoanesber.graphql_employee_management.entity.TitleEmployeeId;

/**
 * Mapper class for converting between TitleEmployee and TitleEmployeeDTO.
 * This class provides methods to convert a TitleEmployee entity to a DTO and vice versa.
 */

public class TitleEmployeeMapper {
    public static TitleEmployeeDTO toDTO(TitleEmployee te) {
        if (te == null) {
            return null;
        }

        return new TitleEmployeeDTO(
                te.getId().getTitle(),
                te.getId().getFromDate(),
                te.getToDate()
        );
    }

    public static TitleEmployee toEntity(TitleEmployeeDTO te) {
        if (te == null) {
            return null;
        }

        TitleEmployee titleEmployee = new TitleEmployee();
        titleEmployee.setId(new TitleEmployeeId(
                null,
                te.getTitle(),
                te.getFromDate()
        ));
        titleEmployee.setToDate(te.getToDate());

        return titleEmployee;
    }
}
