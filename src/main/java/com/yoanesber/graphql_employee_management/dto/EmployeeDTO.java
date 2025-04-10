package com.yoanesber.graphql_employee_management.dto;

import java.sql.Date;
import java.time.OffsetDateTime;
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
 * EmployeeDTO is a Data Transfer Object (DTO) used for transferring employee data between layers.
 * It contains fields that represent the employee's attributes, along with related entities (departments, salaries, titles).
 * The class also includes constructors for converting from the Employee entity to the DTO and vice versa.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class EmployeeDTO {
    private Long id;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;
    private Boolean activeStatus;
    private Long createdBy;
    private OffsetDateTime createdDate;
    private Long updatedBy;
    private OffsetDateTime updatedDate;
    private List<DepartmentEmployeeDTO> departments = new ArrayList<>();
    private List<SalaryEmployeeDTO> salaries = new ArrayList<>();
    private List<TitleEmployeeDTO> titles = new ArrayList<>();

    // Constructor to convert from Employee entity to EmployeeDTO
    // This constructor is useful when you want to create a DTO from an existing entity.
    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.birthDate = employee.getBirthDate();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName(); 
        this.gender = employee.getGender();
        this.hireDate = employee.getHireDate();
        this.activeStatus = employee.getActiveStatus();
        this.createdBy = employee.getCreatedBy();
        this.createdDate = employee.getCreatedDate();
        this.updatedBy = employee.getUpdatedBy();
        this.updatedDate = employee.getUpdatedDate();
        this.departments = employee.getDepartments().stream().map(DepartmentEmployeeDTO::new).collect(Collectors.toList());
        this.salaries = employee.getSalaries().stream().map(SalaryEmployeeDTO::new).collect(Collectors.toList());
        this.titles = employee.getTitles().stream().map(TitleEmployeeDTO::new).collect(Collectors.toList());
    }
}
