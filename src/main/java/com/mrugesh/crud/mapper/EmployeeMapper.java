package com.mrugesh.crud.mapper;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto employeeToEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail()
                );
    }

    public static Employee employeeDtoToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
                );
    }
}
