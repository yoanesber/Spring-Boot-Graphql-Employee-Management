package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Entity // Indicates that this class is an entity and is mapped to a database table
@Table(name = "employee") // name of the table in the database
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "active", nullable = false)
    private Boolean activeStatus;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @Column(name = "updated_date", nullable = false)
    private OffsetDateTime updatedDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy is the name of the field in the other entity that maps this relationship
    // cascade = CascadeType.ALL means that if an Employee is deleted, all related DepartmentEmployee, SalaryEmployee, and TitleEmployee will also be deleted
    // orphanRemoval = true means that if a DepartmentEmployee, SalaryEmployee, or TitleEmployee is removed from the list, it will be deleted from the database
    private List<DepartmentEmployee> departments = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy is the name of the field in the other entity that maps this relationship
    // cascade = CascadeType.ALL means that if an Employee is deleted, all related DepartmentEmployee, SalaryEmployee, and TitleEmployee will also be deleted
    // orphanRemoval = true means that if a DepartmentEmployee, SalaryEmployee, or TitleEmployee is removed from the list, it will be deleted from the database
    private List<SalaryEmployee> salaries = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy is the name of the field in the other entity that maps this relationship
    // cascade = CascadeType.ALL means that if an Employee is deleted, all related DepartmentEmployee, SalaryEmployee, and TitleEmployee will also be deleted
    // orphanRemoval = true means that if a DepartmentEmployee, SalaryEmployee, or TitleEmployee is removed from the list, it will be deleted from the database
    private List<TitleEmployee> titles = new ArrayList<>();
}
