package com.mrugesh.crud.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {

    private String firstName;
    private Integer salary;
    private Integer birthYear;
}
