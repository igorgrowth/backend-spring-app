package com.manage.company.company.mapper;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.model.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .employeeList(project.getEmployeeList()
                        .stream()
                        .map(EmployeeMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
