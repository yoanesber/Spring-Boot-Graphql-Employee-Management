package com.yoanesber.graphql_employee_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class SalaryEmployeeId {
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    
    @Column(name = "from_date", nullable = false)
    private Date fromDate;
}
