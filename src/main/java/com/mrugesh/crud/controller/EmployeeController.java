package com.mrugesh.crud.controller;

import com.mrugesh.crud.dto.EmployeeDto;
import com.mrugesh.crud.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    public final EmployeeService employeeService;

//    @GetMapping("/search&pagination")
//    public ResponseEntity<Page<EmployeeDto>> test(@RequestParam Map<String,String> params) {
//        Page<EmployeeDto>  employeeDto = this.employeeService.searchEmployeeWithPagination(params);
//        return ResponseEntity.ok(employeeDto);
//    }

    @GetMapping("/filtering&pagination&sorting")
    public ResponseEntity<Page<EmployeeDto>> test(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "[{\"field\":\"firstName\",\"direction\":\"desc\"}]") String sort,
            @RequestParam(name = "first_name", required = false) String firstName,
            @RequestParam(name = "salary", required = false) Integer salary,
            @RequestParam(name = "birth_year", required = false) Integer birthYear
    ) {

        Page<EmployeeDto>  employeeDto = employeeService.searchEmployeeWithPaginationSortingAndFiltering(
                EmployeeDto.builder()
                        .firstName(firstName)
                        .salary(salary)
                        .birthYear(birthYear)
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build());

        return ResponseEntity.ok(employeeDto);
    }




}
