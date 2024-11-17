package com.atharva.crud.repository.specification;

import com.atharva.crud.dto.FilterDto;
import com.atharva.crud.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> getSpecification(FilterDto filterDto) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filterDto.getSalary() != null) {
                predicates.add(criteriaBuilder.equal(root.get("salary"),filterDto.getSalary()));
            }

            if (filterDto.getBirthYear() != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthYear"),filterDto.getBirthYear()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
