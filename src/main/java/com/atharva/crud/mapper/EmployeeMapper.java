package com.atharva.crud.mapper;

import com.atharva.crud.dto.EmployeeOutDto;
import com.atharva.crud.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeOutDto employeeEntityToEmployeeDto(Employee employee);
}
