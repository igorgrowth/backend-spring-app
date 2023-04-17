package com.manage.company.company.mapper;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.entity.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .employeeList(project.getEmployeeList()
                        .stream()
                        .map(EmployeeMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
    public static Project toEntity(ProjectDTO projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .employeeList(projectDTO.getEmployeeList()
                        .stream()
                        .map(EmployeeMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
