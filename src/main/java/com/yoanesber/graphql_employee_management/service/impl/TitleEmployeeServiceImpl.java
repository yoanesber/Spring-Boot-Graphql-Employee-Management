package com.yoanesber.graphql_employee_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.graphql_employee_management.entity.TitleEmployee;
import com.yoanesber.graphql_employee_management.repository.TitleEmployeeRepository;
import com.yoanesber.graphql_employee_management.service.TitleEmployeeService;

/**
 * TitleEmployeeServiceImpl is an implementation of the TitleEmployeeService interface.
 * It provides methods to manage title employee records in the database.
 * The class is annotated with @Service, indicating that it's a service layer component in the Spring context.
 * It uses the TitleEmployeeRepository to perform CRUD operations on title employee records.
 * The saveTitleEmployee method is annotated with @Transactional, ensuring that the operation is performed within a transaction.
 */

@Service
public class TitleEmployeeServiceImpl implements TitleEmployeeService {
    
    private final TitleEmployeeRepository titleEmployeeRepository;

    public TitleEmployeeServiceImpl(TitleEmployeeRepository titleEmployeeRepository) {
        this.titleEmployeeRepository = titleEmployeeRepository;
    }

    @Override
    @Transactional
    public TitleEmployee saveTitleEmployee(TitleEmployee titleEmployee) {
        Assert.notNull(titleEmployee, "TitleEmployee cannot be null");

        return titleEmployeeRepository.save(titleEmployee);
    }
}
