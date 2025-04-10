package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.graphql_employee_management.dto.DepartmentDTO;

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Entity // Indicates that this class is an entity and is mapped to a database table
@Table(name = "department")    // name of the table in the database
public class Department {
    @Id
    @Column(name = "id", nullable = false, length = 4)
    private String id;

    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private OffsetDateTime updatedDate;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentEmployee> departmentEmployees = new ArrayList<>();

    public Department(DepartmentDTO departmentDTO) {
        this.id = departmentDTO.getId();
        this.deptName = departmentDTO.getDeptName();
        this.active = departmentDTO.getActive();
        this.createdBy = departmentDTO.getCreatedBy();
        this.createdDate = departmentDTO.getCreatedDate();
        this.updatedBy = departmentDTO.getUpdatedBy();
        this.updatedDate = departmentDTO.getUpdatedDate();
    }
}
