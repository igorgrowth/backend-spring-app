package com.manage.company.company.service;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.model.Project;

import java.util.List;

public interface ProjectService {
    ProjectDTO save(Project project);
    List<ProjectDTO> findAll();
    ProjectDTO getById(Long id);
    ProjectDTO getByName(String name);
    ProjectDTO delete(Long id);
    ProjectDTO update(Project project);

}
