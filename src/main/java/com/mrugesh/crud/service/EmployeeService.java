package com.mrugesh.crud.service;

import com.mrugesh.crud.dto.EmployeeDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

    void deleteEmployee(Long employeeId);

    //changes
    List<EmployeeDto> getAllEmployeesWithSorting(String field);

    Page<EmployeeDto> getAllEmployeesWithPagination(int offset, int pageSize);

//    List<EmployeeDto> searchEmployees(String keyword);

    List<EmployeeDto> getAllEmployeesWithFilter(String firstName, String lastName);
}

