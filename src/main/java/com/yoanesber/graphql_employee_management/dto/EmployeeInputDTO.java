package com.yoanesber.graphql_employee_management.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * EmployeeDTO is a Data Transfer Object (DTO) used for transferring employee data between layers.
 * It contains fields that represent the employee's attributes, along with related entities (departments, salaries, titles).
 * The class also includes constructors for converting from the Employee entity to the DTO and vice versa.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class EmployeeInputDTO {
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;
    private Boolean active;
    private Long createdBy;
    private Long updatedBy;

    private List<DepartmentEmployeeDTO> departments = new ArrayList<>();
    private List<SalaryEmployeeDTO> salaries = new ArrayList<>();
    private List<TitleEmployeeDTO> titles = new ArrayList<>();
}
