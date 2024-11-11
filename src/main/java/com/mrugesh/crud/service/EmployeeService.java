package com.mrugesh.crud.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.dto.FilterDto;
import com.mrugesh.crud.dto.SortDto;
import com.mrugesh.crud.entity.Employee;
import com.mrugesh.crud.mapper.EmployeeMapper;
import com.mrugesh.crud.repository.EmployeeRepository;
import com.mrugesh.crud.repository.specification.EmployeeSpecification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<EmployeeDto> searchEmployeeWithPaginationSortingAndFiltering(EmployeeDto employeeDto) {

        FilterDto filterDto = FilterDto.builder()
                .firstName(employeeDto.getFirstName())
                .salary(employeeDto.getSalary())
                .birthYear(employeeDto.getBirthYear())
                .build();

        List<SortDto> sortDtos = jsonStringToSortDto(employeeDto.getSort());
        List<Sort.Order> orders = new ArrayList<>();

        if (sortDtos != null) {
            for(SortDto sortDto: sortDtos) {
                Sort.Direction direction = Objects.equals(sortDto.getDirection(), "desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction,sortDto.getField()));
            }
        }

        PageRequest pageRequest = PageRequest.of(employeeDto.getPage(),employeeDto.getSize(),Sort.by(orders));

        Specification<Employee> specification = EmployeeSpecification.getSpecification(filterDto);

        Page<Employee> employees = employeeRepository.findAll(specification,pageRequest);
        log.info("data --------- {}",pageRequest);
        return employees.map(EmployeeMapper.INSTANCE::employeeEntityToEmployeeDto);
    }

    private List<SortDto> jsonStringToSortDto(String jsonString) {
        try {
            ObjectMapper obj = new ObjectMapper();
            return obj.readValue(jsonString, new TypeReference<>() {});
        } catch (Exception e) {
            log.info("Exception: {}",e);
            return null;
        }
    }
}
