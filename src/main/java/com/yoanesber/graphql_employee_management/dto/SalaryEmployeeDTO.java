package com.yoanesber.graphql_employee_management.dto;

import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * SalaryEmployeeDTO is a Data Transfer Object (DTO) used for managing salary employee records.
 * It contains fields that are required when creating or updating salary employee records, along with validation annotations.
 * The class uses Lombok annotations to reduce boilerplate code for getters, setters, and constructors.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class SalaryEmployeeDTO {

    @NotNull(message = "From Date cannot be null")
    private Date fromDate;

    @NotNull(message = "Amount cannot be null")
    private Long amount;

    @NotNull(message = "To Date cannot be null")
    private Date toDate;
}
