package com.atharva.crud.controller;

import com.atharva.crud.service.EmployeeService;
import com.atharva.crud.dto.EmployeeInDto;
import com.atharva.crud.dto.EmployeeOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    public final EmployeeService employeeService;


    @GetMapping("/filtering&pagination&sorting")
    public ResponseEntity<Page<EmployeeOutDto>> test(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sort", defaultValue = "[{\"field\":\"firstName\",\"direction\":\"desc\"}]") String sort,
            @RequestParam(name = "first_name", required = false) String firstName,
            @RequestParam(name = "salary", required = false) Integer salary,
            @RequestParam(name = "birth_year", required = false) Integer birthYear
    ) {

        Page<EmployeeOutDto> employeeDto = employeeService.searchEmployeeWithPaginationSortingAndFiltering(
                EmployeeInDto.builder()
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
