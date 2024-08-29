package com.mrugesh.crud.repository;

import com.mrugesh.crud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query()
    List<Employee> searchEmplooyees(String Keyword);

    List<Employee> findByLastName(String name);

}
