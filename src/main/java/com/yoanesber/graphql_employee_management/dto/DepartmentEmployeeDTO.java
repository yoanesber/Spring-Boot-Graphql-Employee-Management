package com.yoanesber.graphql_employee_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;

/*
 * DepartmentEmployeeDTO is a Data Transfer Object (DTO) used for managing department employee records.
 * It contains fields that are required when creating or updating department employee records, along with validation annotations.
 * The class uses Lombok annotations to reduce boilerplate code for getters, setters, and constructors.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class DepartmentEmployeeDTO {

    @NotBlank(message = "Department ID cannot be blank")
    private String departmentId;

    @NotNull(message = "From Date cannot be null")
    private Date fromDate;

    @NotNull(message = "To Date cannot be null")
    private Date toDate;

    // Constructor to convert from DepartmentEmployee entity to DepartmentEmployeeDTO
    // This constructor is useful when you want to create a DTO from an existing entity.
    public DepartmentEmployeeDTO(DepartmentEmployee departmentEmployee) {
        if (departmentEmployee != null && departmentEmployee.getId() != null) {
            this.departmentId = departmentEmployee.getId().getDepartmentId();
        }
        
        this.fromDate = departmentEmployee.getFromDate();
        this.toDate = departmentEmployee.getToDate();
    }
}
