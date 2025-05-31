package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the association between a Department and an Employee.
 * This entity is used to track which employees belong to which departments,
 * along with the dates they joined and left the department.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Entity
@Table(name = "department_employee") // name of the table in the database
public class DepartmentEmployee {
    @EmbeddedId
    private DepartmentEmployeeId id;
    
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "department_id")
    private Department department;

    @Override
    public String toString() {
        return "DepartmentEmployee{" +
                "id=" + id.toString() +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentEmployee)) return false;

        DepartmentEmployee that = (DepartmentEmployee) o;

        return id.equals(that.id) &&
               fromDate.equals(that.fromDate) &&
               toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromDate, toDate);
    }
}
