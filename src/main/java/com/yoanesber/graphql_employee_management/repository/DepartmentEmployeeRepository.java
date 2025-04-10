package com.yoanesber.graphql_employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;
import com.yoanesber.graphql_employee_management.entity.DepartmentEmployeeId;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {
    
}
