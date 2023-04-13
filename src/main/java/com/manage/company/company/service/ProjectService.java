package com.manage.company.company.service;

import com.manage.company.company.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO save(ProjectDTO projectDTO);
    List<ProjectDTO> findAll();
    ProjectDTO getById(Long id);
    ProjectDTO getByName(String name);
    ProjectDTO delete(Long id);
    ProjectDTO update(ProjectDTO projectDTO);

}
