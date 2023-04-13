package com.manage.company.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;
    private String name;
    List<EmployeeDTO> employeeList = new ArrayList<>();
}
