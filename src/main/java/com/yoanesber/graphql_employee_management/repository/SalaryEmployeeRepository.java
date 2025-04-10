package com.yoanesber.graphql_employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.graphql_employee_management.entity.SalaryEmployee;
import com.yoanesber.graphql_employee_management.entity.SalaryEmployeeId;

@Repository
public interface SalaryEmployeeRepository extends JpaRepository<SalaryEmployee, SalaryEmployeeId> {

}
