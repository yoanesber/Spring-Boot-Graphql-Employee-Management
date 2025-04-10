package com.yoanesber.graphql_employee_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.entity.SalaryEmployee;
import com.yoanesber.graphql_employee_management.repository.SalaryEmployeeRepository;
import com.yoanesber.graphql_employee_management.service.SalaryEmployeeService;

/**
 * SalaryEmployeeServiceImpl is an implementation of the SalaryEmployeeService interface.
 * It provides methods to manage salary employee records in the database.
 * The class is annotated with @Service, indicating that it's a service layer component in the Spring context.
 * It uses the SalaryEmployeeRepository to perform CRUD operations on salary employee records.
 * The saveSalaryEmployee method is annotated with @Transactional, ensuring that the operation is performed within a transaction.
 */

@Service
public class SalaryEmployeeServiceImpl implements SalaryEmployeeService {

    private final SalaryEmployeeRepository salaryEmployeeRepository;

    public SalaryEmployeeServiceImpl(SalaryEmployeeRepository salaryEmployeeRepository) {
        this.salaryEmployeeRepository = salaryEmployeeRepository;
    }

    @Override
    @Transactional
    public SalaryEmployee saveSalaryEmployee(SalaryEmployee salaryEmployee) {
        Assert.notNull(salaryEmployee, "SalaryEmployee cannot be null");

        return salaryEmployeeRepository.save(salaryEmployee);
    }
}
