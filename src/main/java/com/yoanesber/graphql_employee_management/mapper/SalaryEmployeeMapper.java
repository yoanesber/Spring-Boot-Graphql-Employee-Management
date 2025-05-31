package com.yoanesber.graphql_employee_management.mapper;

import com.yoanesber.graphql_employee_management.dto.SalaryEmployeeDTO;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployee;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployeeId;

/**
 * Mapper class for converting between SalaryEmployee and SalaryEmployeeDTO.
 * This class provides methods to convert a SalaryEmployee entity to a DTO and vice versa.
 */

public class SalaryEmployeeMapper {
    public static SalaryEmployeeDTO toDTO(SalaryEmployee se) {
        if (se == null) {
            return null;
        }

        return new SalaryEmployeeDTO(
                se.getId().getFromDate(),
                se.getAmount(),
                se.getToDate()
        );
    }

    public static SalaryEmployee toEntity(SalaryEmployeeDTO se) {
        if (se == null) {
            return null;
        }

        SalaryEmployee salaryEmployee = new SalaryEmployee();
        salaryEmployee.setId(new SalaryEmployeeId(
                null,
                se.getFromDate()
        ));
        salaryEmployee.setAmount(se.getAmount());
        salaryEmployee.setToDate(se.getToDate());

        return salaryEmployee;
    }
}
