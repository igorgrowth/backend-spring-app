package com.manage.company.company.repository;

import com.manage.company.company.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
  Page<Employee> findAll(Pageable pageable);
  Optional<Employee> findByEmail(String email);
}
