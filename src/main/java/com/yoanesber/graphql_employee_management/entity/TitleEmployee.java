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
 * Represents the association between a Title and an Employee.
 * This entity is used to track the title of an employee along with the date from which the title is effective.
 */

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Entity
@Table(name = "title") // name of the table in the database
public class TitleEmployee {
    @EmbeddedId
    private TitleEmployeeId id;

    @Column(name = "to_date")
    private Date toDate;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Override
    public String toString() {
        return "TitleEmployee{" +
                "id=" + id.toString() +
                ", toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleEmployee)) return false;

        TitleEmployee that = (TitleEmployee) o;

        return id.equals(that.id) &&
               toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toDate);
    }
}
