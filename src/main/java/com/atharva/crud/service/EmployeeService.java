package com.atharva.crud.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.atharva.crud.dto.EmployeeInDto;
import com.atharva.crud.dto.EmployeeOutDto;
import com.atharva.crud.dto.FilterDto;
import com.atharva.crud.dto.SortDto;
import com.atharva.crud.entity.Employee;
import com.atharva.crud.mapper.EmployeeMapper;
import com.atharva.crud.repository.EmployeeRepository;
import com.atharva.crud.repository.specification.EmployeeSpecification;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<EmployeeOutDto> searchEmployeeWithPaginationSortingAndFiltering(EmployeeInDto employeeInDto) {

        FilterDto filterDto = FilterDto.builder()
                .salary(employeeInDto.getSalary())
                .birthYear(employeeInDto.getBirthYear())
                .build();

        List<SortDto> sortDtos = jsonStringToSortDto(employeeInDto.getSort());
        List<Sort.Order> orders = new ArrayList<>();

        if (sortDtos != null) {
            for(SortDto sortDto: sortDtos) {
                Sort.Direction direction = Objects.equals(sortDto.getDirection(), "desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction,sortDto.getField()));
            }
        }

        log.info("sort{}",sortDtos);
        log.info("filter{}",filterDto);

        PageRequest pageRequest = PageRequest.of(employeeInDto.getPage(), employeeInDto.getSize(),Sort.by(orders));

        Specification<Employee> specification = EmployeeSpecification.getSpecification(filterDto);

        Page<Employee> employees = employeeRepository.findAll(specification,pageRequest);

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

    public byte[] generateFileContent() {


        List<Employee> employees = employeeRepository.fetchEmployees();
        log.info("Fetched employees: {}", employees);

        List<EmployeeOutDto> employeeDtos = employees.stream().map(EmployeeMapper.INSTANCE::employeeEntityToEmployeeDto).toList();

        return writeEmployees(employeeDtos);

    }

    private byte[] writeEmployees(List<EmployeeOutDto> employeeDtos) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(outputStream);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            List<String[]> data = employeeDtos.stream()
                    .map(emp -> new String[] {
                            String.valueOf(emp.getId()),
                            emp.getFirstName(),
                            emp.getLastName(),
                            String.valueOf(emp.getBirthYear()),
                            String.valueOf(emp.getSalary())
                    })
                    .toList();

            csvWriter.writeAll(data);
            csvWriter.flush();

            return outputStream.toByteArray();

        } catch (IOException e) {
            log.info("Exception: {}",e);
            return new byte[0];
        }

    }


}
