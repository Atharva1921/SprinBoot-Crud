package com.atharva.crud.controller;

import com.atharva.crud.service.EmployeeService;
import com.atharva.crud.dto.EmployeeInDto;
import com.atharva.crud.dto.EmployeeOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

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

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile() {

        byte[] fileContent = employeeService.generateFileContent();

        InputStreamResource resource = new InputStreamResource(
                new ByteArrayInputStream(fileContent)
        );

        String fileName = "testDownload.csv";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
