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
 * Represents the association between a Salary and an Employee.
 * This entity is used to track the salary amount and the date until which the salary is valid.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Entity
@Table(name = "salary") // name of the table in the database
public class SalaryEmployee {
    @EmbeddedId
    private SalaryEmployeeId id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Override
    public String toString() {
        return "SalaryEmployee{" +
                "id=" + id.toString() +
                ", amount=" + amount +
                ", toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryEmployee)) return false;

        SalaryEmployee that = (SalaryEmployee) o;

        return id.equals(that.id) &&
               amount.equals(that.amount) &&
               toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, toDate);
    }
}
