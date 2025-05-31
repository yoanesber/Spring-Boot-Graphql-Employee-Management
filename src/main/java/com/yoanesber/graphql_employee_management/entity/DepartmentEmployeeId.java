package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the composite primary key for the DepartmentEmployee entity.
 * This class is used to uniquely identify an employee within a specific department.
 * It is marked as @Embeddable so it can be embedded in the DepartmentEmployee entity.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Embeddable
public class DepartmentEmployeeId {
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "department_id", nullable = false, length = 4)
    private String departmentId;

    @Override
    public String toString() {
        return "DepartmentEmployeeId{" +
                "employeeId=" + employeeId +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentEmployeeId)) return false;

        DepartmentEmployeeId that = (DepartmentEmployeeId) o;

        return employeeId.equals(that.employeeId) && 
            departmentId.equals(that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentId);
    }
}
