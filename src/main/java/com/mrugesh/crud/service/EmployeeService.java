package com.mrugesh.crud.service;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.entity.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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

    List<EmployeeDto> searchEmployeeByCriteria(SearchCriteria searchCriteria);

    Page<EmployeeDto> searchEmployeeWithPagination(Map<String, String> params);
}

