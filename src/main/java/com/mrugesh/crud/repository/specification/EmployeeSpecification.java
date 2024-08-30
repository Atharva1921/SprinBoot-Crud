package com.mrugesh.crud.repository.specification;

import com.mrugesh.crud.entity.Employee;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecification {

    public static Specification<Employee> filterEmployee(String firstName, String lastName) {
        return (root, query, criteriaBuilder) -> {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstname"), StringUtils.isBlank(firstName) ? likePattern("") : firstName);
            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastname"), StringUtils.isBlank(lastName) ? likePattern("") : lastName);

            return criteriaBuilder.and(firstNamePredicate,lastNamePredicate);
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
