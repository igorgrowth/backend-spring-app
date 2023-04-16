package com.manage.company.company.mapper;

import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.model.Employee;

public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .projectDTO(ProjectMapper.toDTO(employee.getProject() != null ? employee.getProject() : null))
                .build();
    }
    public static Employee toEntity(EmployeeDTO employeeDTO){
        return Employee.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .position(employeeDTO.getPosition())
                .project(employeeDTO.getProjectDTO() != null ? ProjectMapper.toEntity(employeeDTO.getProjectDTO()) : null)
                .build();
    }
}
