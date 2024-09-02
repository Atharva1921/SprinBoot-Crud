package com.mrugesh.crud.repository.specification;

import com.mrugesh.crud.entity.Employee;
import com.mrugesh.crud.entity.SearchCriteria;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeSpecification {

    public static Specification<Employee> createSpecification(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(searchCriteria.getSearchKey()),"%" + searchCriteria.getSearchValue() + "%");
    }

    public static Specification<Employee> getSpecification(Map<String, String> params) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            params.forEach((key,value) -> {
                Predicate predicate = criteriaBuilder.like(root.get(key),"%" + value + "%");
                predicates.add(predicate);
            });

            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            return finalPredicate;

        };
    }

}
