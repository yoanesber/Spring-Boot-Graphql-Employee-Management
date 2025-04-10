package com.yoanesber.graphql_employee_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.graphql_employee_management.entity.Employee;

/*
 * EmployeeCreateDTO is a Data Transfer Object (DTO) used for creating new employee records.
 * It contains fields that are required when creating a new employee, along with validation annotations.
 * The class also includes constructors for converting from the Employee entity to the DTO and vice versa.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class EmployeeCreateDTO {

    @NotNull(message = "Birth Date cannot be null")
    private Date birthDate;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotNull(message = "Hire Date cannot be null")
    private Date hireDate;

    @NotNull(message = "Active Status cannot be null")
    private Boolean activeStatus;

    @NotNull(message = "Created By cannot be null")
    private Long createdBy;
    
    // List of related entities (departments, salaries, titles) as DTOs
    // These lists will be populated with the corresponding DTOs when creating a new employee.
    private List<DepartmentEmployeeDTO> departments = new ArrayList<>();
    private List<SalaryEmployeeDTO> salaries = new ArrayList<>();
    private List<TitleEmployeeDTO> titles = new ArrayList<>();

    // Constructor to convert from Employee entity to EmployeeCreateDTO
    // This constructor is useful when you want to create a DTO from an existing entity.
    public EmployeeCreateDTO(Employee employee) {
        this.birthDate = employee.getBirthDate();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName(); 
        this.gender = employee.getGender();
        this.hireDate = employee.getHireDate();
        this.activeStatus = employee.getActiveStatus();
        this.createdBy = employee.getCreatedBy();
        this.departments = employee.getDepartments().stream().map(DepartmentEmployeeDTO::new).collect(Collectors.toList());
        this.salaries = employee.getSalaries().stream().map(SalaryEmployeeDTO::new).collect(Collectors.toList());
        this.titles = employee.getTitles().stream().map(TitleEmployeeDTO::new).collect(Collectors.toList());
    }
}
