package com.manage.company.company.service;

import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO save(Employee employee );
    Page<EmployeeDTO> getAll(Pageable pageable);
    EmployeeDTO getById(Long id);
    EmployeeDTO getByEmail(String email);
    EmployeeDTO delete(Long id);
    EmployeeDTO update(Employee employee);
}
