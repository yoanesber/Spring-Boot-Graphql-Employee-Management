package com.yoanesber.graphql_employee_management.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.graphql_employee_management.entity.Employee;

/**
 * Repository interface for managing Employee entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 * It includes methods to find all employees that are not deleted and to find an employee by its ID while ensuring it is not marked as deleted.
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByIsDeletedFalse(Sort sort);

    Employee findByIdAndIsDeletedFalse(Long id);

    Boolean existsByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, Date birthDate);

    @Override
    default List<Employee> findAll(Sort sort) {
        // Add where clause to filter by isDeleted = false
        return findAllByIsDeletedFalse(sort);
    }

    @Override
    default Optional<Employee> findById(Long id) {
        // Add where clause to filter by isDeleted = false
        return Optional.ofNullable(findByIdAndIsDeletedFalse(id));
    }
}