package com.yoanesber.graphql_employee_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.entity.DepartmentEmployee;
import com.yoanesber.graphql_employee_management.repository.DepartmentEmployeeRepository;
import com.yoanesber.graphql_employee_management.service.DepartmentEmployeeService;

/*
 * DepartmentEmployeeServiceImpl is an implementation of the DepartmentEmployeeService interface.
 * It provides methods to manage department employee records in the database.
 * The class is annotated with @Service, indicating that it's a service layer component in the Spring context.
 * It uses the DepartmentEmployeeRepository to perform CRUD operations on department employee records.
 * The saveDepartmentEmployee method is annotated with @Transactional, ensuring that the operation is performed within a transaction.
 */

@Service
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService {

    private final DepartmentEmployeeRepository departmentEmployeeRepository;

    public DepartmentEmployeeServiceImpl(DepartmentEmployeeRepository departmentEmployeeRepository) {
        this.departmentEmployeeRepository = departmentEmployeeRepository;
    }

    @Override
    @Transactional
    public DepartmentEmployee saveDepartmentEmployee(DepartmentEmployee departmentEmployee) {
        Assert.notNull(departmentEmployee, "DepartmentEmployee cannot be null");

        return departmentEmployeeRepository.save(departmentEmployee);
    }
}
