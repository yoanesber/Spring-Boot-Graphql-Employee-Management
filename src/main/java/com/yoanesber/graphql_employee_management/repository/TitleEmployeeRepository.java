package com.yoanesber.graphql_employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.graphql_employee_management.entity.TitleEmployee;
import com.yoanesber.graphql_employee_management.entity.TitleEmployeeId;

@Repository
public interface TitleEmployeeRepository extends JpaRepository<TitleEmployee, TitleEmployeeId> {

}
