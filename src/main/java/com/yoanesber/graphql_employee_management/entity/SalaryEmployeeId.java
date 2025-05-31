package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.sql.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the composite primary key for the SalaryEmployee entity.
 * This class is used to uniquely identify a salary record for an employee based on the employee ID and the date from which the salary is effective.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Embeddable
public class SalaryEmployeeId {
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Override
    public String toString() {
        return "SalaryEmployeeId{" +
                "employeeId=" + employeeId +
                ", fromDate=" + fromDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryEmployeeId)) return false;

        SalaryEmployeeId that = (SalaryEmployeeId) o;

        return employeeId.equals(that.employeeId) &&
               fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, fromDate);
    }
}
