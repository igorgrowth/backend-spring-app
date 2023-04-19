package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.entity.Employee;
import com.manage.company.company.exeption.EntityAlreadyExistException;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.EmployeeMapper;
import com.manage.company.company.repository.EmployeeRepo;
import com.manage.company.company.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;


    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        Optional<Employee> existingEmployee = employeeRepo.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
            throw new EntityAlreadyExistException("Email", employee.getEmail() + " already exists.");
        }

        Employee savedEmployee = employeeRepo.save(employee);
        log.info("Saving employee: {}", savedEmployee);
        return EmployeeMapper.toDTO(savedEmployee);
    }

    @Override
    public Page<EmployeeDTO> getAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepo.findAll(pageable);
        List<EmployeeDTO> employeeDTOs = employeePage.getContent().stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Getting all employees with pagination: {}", pageable);
        return new PageImpl<>(employeeDTOs, pageable, employeePage.getTotalElements());
    }


    @Override
    public EmployeeDTO getById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "id: " + id));
        log.info("Getting employee by id: {}", id);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO getByEmail(String email) {
        Employee employee = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "email: " + email));
        log.info("Getting employee by email: {}", employee);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO delete(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "id: " + id));
        employeeRepo.delete(employee);
        log.info("Deleting employee with id: {}", id);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        Employee existingEmployee = employeeRepo.findById(employee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "id: " + employee.getId()));
        existingEmployee.setId(employee.getId());
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setProject(employee.getProject());
        Employee updatedEmployee = employeeRepo.save(existingEmployee);
        log.info("Updated employee: {}", updatedEmployee);
        return EmployeeMapper.toDTO(updatedEmployee);
    }
}
