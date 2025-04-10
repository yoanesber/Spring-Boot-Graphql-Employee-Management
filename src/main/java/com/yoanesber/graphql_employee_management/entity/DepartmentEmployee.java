package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.sql.Date;
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
@Table(name = "department_employee") // name of the table in the database
public class DepartmentEmployee {
    @EmbeddedId
    private DepartmentEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("departmentId")
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    // Constructor to ensure ID is properly set
    public DepartmentEmployee(Employee employee, Department department) {
        this.employee = employee;
        this.department = department;
        this.id = new DepartmentEmployeeId(employee.getId(), department.getId()); // Ensure ID is set
    }
}
