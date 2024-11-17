package com.atharva.crud.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer birthYear;
    private Integer salary;

    private Integer page;
    private Integer size;
    private String sort;

}
