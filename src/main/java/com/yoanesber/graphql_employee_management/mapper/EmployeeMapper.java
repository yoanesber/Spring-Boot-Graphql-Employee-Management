package com.yoanesber.graphql_employee_management.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yoanesber.graphql_employee_management.dto.EmployeeDTO;
import com.yoanesber.graphql_employee_management.dto.EmployeeInputDTO;
import com.yoanesber.graphql_employee_management.entity.Employee;

/**
 * Mapper class for converting between Employee and EmployeeDTO.
 * This class provides methods to convert an Employee entity to a DTO and vice versa.
 */

public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee e) {
        if (e == null) {
            return null;
        }

        return new EmployeeDTO(
                e.getId(),
                e.getBirthDate(),
                e.getFirstName(),
                e.getLastName(),
                e.getGender(),
                e.getHireDate(),
                e.getActive(),
                e.getDepartments().stream()
                    .map(DepartmentEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                e.getSalaries().stream()
                    .map(SalaryEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                e.getTitles().stream()
                    .map(TitleEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public static EmployeeInputDTO toInputDTO(Employee e) {
        if (e == null) {
            return null;
        }

        return new EmployeeInputDTO(
                e.getBirthDate(),
                e.getFirstName(),
                e.getLastName(),
                e.getGender(),
                e.getHireDate(),
                e.getActive(),
                e.getCreatedBy(),
                e.getUpdatedBy(),
                e.getDepartments().stream()
                    .map(DepartmentEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                e.getSalaries().stream()
                    .map(SalaryEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                e.getTitles().stream()
                    .map(TitleEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public static List<EmployeeDTO> toDTOList(List<Employee> eList) {
        if (eList == null || eList.isEmpty()) {
            return List.of();
        }

        return eList.stream()
            .map(EmployeeMapper::toDTO)
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static Employee toEntity(EmployeeDTO e) {
        if (e == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(e.getId());
        employee.setBirthDate(e.getBirthDate());
        employee.setFirstName(e.getFirstName());
        employee.setLastName(e.getLastName());
        employee.setGender(e.getGender());
        employee.setHireDate(e.getHireDate());
        employee.setActive(e.getActive());
        employee.setDepartments(
            e.getDepartments().stream()
                .map(DepartmentEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setSalaries(
            e.getSalaries().stream()
                .map(SalaryEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setTitles(
            e.getTitles().stream()
                .map(TitleEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));

        return employee;
    }

    public static Employee toEntity(EmployeeInputDTO e) {
        if (e == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setBirthDate(e.getBirthDate());
        employee.setFirstName(e.getFirstName());
        employee.setLastName(e.getLastName());
        employee.setGender(e.getGender());
        employee.setHireDate(e.getHireDate());
        employee.setActive(e.getActive());
        employee.setCreatedBy(e.getCreatedBy());
        employee.setUpdatedBy(e.getUpdatedBy());
        employee.setDepartments(
            e.getDepartments().stream()
                .map(DepartmentEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setSalaries(
            e.getSalaries().stream()
                .map(SalaryEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setTitles(
            e.getTitles().stream()
                .map(TitleEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));

        return employee;
    }

    public static List<Employee> toEntityList(List<EmployeeDTO> eList) {
        if (eList == null || eList.isEmpty()) {
            return new ArrayList<>();
        }

        return eList.stream()
            .map(EmployeeMapper::toEntity)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
