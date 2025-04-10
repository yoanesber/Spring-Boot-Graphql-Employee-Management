package com.yoanesber.graphql_employee_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * DepartmentCreateDTO is a Data Transfer Object (DTO) used for creating new department records.
 * It contains fields that are required when creating a new department, along with validation annotations.
 * The class uses Lombok annotations to reduce boilerplate code for getters, setters, and constructors.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class DepartmentCreateDTO {
    
    @NotBlank(message = "Department ID cannot be blank")
    private String id;

    @NotBlank(message = "Department Name cannot be blank")
    private String deptName;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @NotNull(message = "Department Created By cannot be null")
    private Long createdBy;
}
