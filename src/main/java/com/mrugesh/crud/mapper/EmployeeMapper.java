package com.mrugesh.crud.mapper;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeEntityToEmployeeDto(Employee employee);
}
