package com.mrugesh.crud.controller;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.entity.SearchCriteria;
import com.mrugesh.crud.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Build Add Employee REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Build Get Employee REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    //Build Get All Employee REST API
    @GetMapping
    public ResponseEntity<List<EmployeeDto>>getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    //sort chanegs
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<EmployeeDto>>getAllEmployeesWithSorting(@PathVariable String field){
        List<EmployeeDto> employees = employeeService.getAllEmployeesWithSorting(field);
        return ResponseEntity.ok(employees);
    }

    //pagination changes
    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<EmployeeDto>>getAllEmployeesWithSorting(@PathVariable int offset, @PathVariable int pageSize){
        Page<EmployeeDto> employees = employeeService.getAllEmployeesWithPagination(offset,pageSize);
        return ResponseEntity.ok(employees);
    }

    //Build Update Employee REST API
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeid,
                                                      @RequestBody EmployeeDto updatedEmployee){
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeid, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    //Build Delete Employee REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully.");

    }

//    //searching changes
//    @GetMapping("/search")
//    public ResponseEntity<List<EmployeeDto>> searchEmployees(String keyword) {
//
//        List<EmployeeDto> Employee = employeeService.searchEmployees(keyword);
//        return new ResponseEntity<>(Employee,HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesWithFiltering(@RequestBody SearchCriteria searchCriteria) {
        List<EmployeeDto>  employeeDto = this.employeeService.searchEmployeeByCriteria(searchCriteria);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/search&pagination")
    public ResponseEntity<Page<EmployeeDto>> test(@RequestParam Map<String,String> params) {
        Page<EmployeeDto>  employeeDto = this.employeeService.searchEmployeeWithPagination(params);
        return ResponseEntity.ok(employeeDto);
    }


}
