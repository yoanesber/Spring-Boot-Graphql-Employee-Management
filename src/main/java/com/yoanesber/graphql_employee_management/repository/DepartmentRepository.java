package com.yoanesber.graphql_employee_management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.graphql_employee_management.entity.Department;

/**
 * Repository interface for managing Department entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 * It includes methods to find all departments that are not deleted and to find a department by its ID while ensuring it is not marked as deleted.
 */

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    List<Department> findAllByIsDeletedFalse(Sort sort);

    Department findByIdAndIsDeletedFalse(String id);

    @Override
    default List<Department> findAll(Sort sort) {
        // Add where clause to filter by isDeleted = false
        return findAllByIsDeletedFalse(sort);
    }

    @Override
    default Optional<Department> findById(String id) {
        // Add where clause to filter by isDeleted = false
        return Optional.ofNullable(findByIdAndIsDeletedFalse(id));
    }
}