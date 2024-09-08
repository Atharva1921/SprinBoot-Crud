package com.mrugesh.crud.service.impl;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.entity.Employee;
import com.mrugesh.crud.entity.SearchCriteria;
import com.mrugesh.crud.exception.ResourceNotFoundException;
import com.mrugesh.crud.mapper.EmployeeMapper;
import com.mrugesh.crud.repository.EmployeeRepository;
import com.mrugesh.crud.repository.specification.EmployeeSpecification;
import com.mrugesh.crud.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto){

        Employee employee = EmployeeMapper.employeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.employeeToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not exist with given id: "+ employeeId));
        return EmployeeMapper.employeeToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee not exists with given id: "+ employeeId)
        );
        employee.setFirstname(updatedEmployee.getFirstName());
        employee.setLastname(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.employeeToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee not exists with given id: "+ employeeId)
        );

        employeeRepository.deleteById(employeeId);

    }

    //changes
    @Override
    public List<EmployeeDto> getAllEmployeesWithSorting(String field) {
        List<Employee> employees =  employeeRepository.findAll(Sort.by(Sort.Direction.ASC,field));
        return employees.stream().map(EmployeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

    //changes
    @Override
    public Page<EmployeeDto> getAllEmployeesWithPagination(int offset, int pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(offset,pageSize));
        return employees.map(EmployeeMapper::employeeToEmployeeDto);
    }


    @Override
    public List<EmployeeDto> searchEmployeeByCriteria(SearchCriteria searchCriteria) {
        final List<Employee> employees = this.employeeRepository.findAll(EmployeeSpecification.createSpecification(searchCriteria));
        return employees.stream().map(EmployeeMapper::employeeToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDto> searchEmployeeWithPagination(Map<String, String> params) {

        int offset = Integer.parseInt(params.get("offset"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        PageRequest pageable = PageRequest.of(offset,pageSize);

        params.remove("offset");
        params.remove("pageSize");

        Specification<Employee> specification = EmployeeSpecification.getSpecification(params);

        final Page<Employee> employees = this.employeeRepository.findAll(specification,pageable);
        return employees.map(EmployeeMapper::employeeToEmployeeDto);
    }
}
