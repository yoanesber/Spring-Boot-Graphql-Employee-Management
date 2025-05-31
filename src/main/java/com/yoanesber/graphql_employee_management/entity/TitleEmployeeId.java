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
 * Represents the composite primary key for the TitleEmployee entity.
 * This class is used to uniquely identify a title record for an employee based on the employee ID, title, and the date from which the title is effective.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Embeddable
public class TitleEmployeeId {
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Override
    public String toString() {
        return "TitleEmployeeId{" +
                "employeeId=" + employeeId +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleEmployeeId)) return false;

        TitleEmployeeId that = (TitleEmployeeId) o;

        return employeeId.equals(that.employeeId) &&
               title.equals(that.title) &&
               fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, title, fromDate);
    }
}
