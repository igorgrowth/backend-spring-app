package com.manage.company.company.service;

import com.manage.company.company.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO );
    Page<EmployeeDTO> getAll(Pageable pageable);
    EmployeeDTO getById(Long id);
    EmployeeDTO getByEmail(String email);
    EmployeeDTO delete(Long id);
    EmployeeDTO update(EmployeeDTO employeeDTO);
}
