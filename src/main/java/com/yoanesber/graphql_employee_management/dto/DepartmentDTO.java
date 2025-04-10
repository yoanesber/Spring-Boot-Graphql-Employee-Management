package com.yoanesber.graphql_employee_management.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.graphql_employee_management.entity.Department;

/***
 * DepartmentDTO is a Data Transfer Object (DTO) that represents the data structure for a department.
 * It is used to transfer data between the application layers, especially when interacting with APIs.
 * This DTO includes fields for department ID, name, active status, and timestamps for creation and updates.
 * It also includes a constructor to convert from the Department entity to this DTO.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class DepartmentDTO {
    private String id;
    private String deptName;
    private Boolean active;
    private Long createdBy;
    private OffsetDateTime createdDate;
    private Long updatedBy;
    private OffsetDateTime updatedDate;

    // Constructor to convert from Department entity to DepartmentDTO
    // This constructor is useful when you want to create a DTO from an existing entity.
    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.deptName = department.getDeptName();
        this.active = department.getActive();
        this.createdBy = department.getCreatedBy();
        this.createdDate = department.getCreatedDate();
        this.updatedBy = department.getUpdatedBy();
        this.updatedDate = department.getUpdatedDate();
    }
}
