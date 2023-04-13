package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.exeption.EntityAlreadyExistException;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.ProjectMapper;
import com.manage.company.company.model.Employee;
import com.manage.company.company.model.Project;
import com.manage.company.company.repository.ProjectRepo;
import com.manage.company.company.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;

    public ProjectServiceImpl(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        if(projectDTO.getEmployeeList() == null){
            projectDTO.setEmployeeList(Collections.emptyList());
        }
        Project project = ProjectMapper.toEntity(projectDTO);
        Optional<Project> existingProject = projectRepo.findByName(project.getName());
        if (existingProject.isPresent()) {
            throw new EntityAlreadyExistException("Project", project.getName() + " already exists.");
        }

        Project savedProject = projectRepo.save(project);
        log.info("Saving project: {}", savedProject);
        return ProjectMapper.toDTO(savedProject);
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> projects = projectRepo.findAll();
        log.info("Found {} projects", projects.size());
        return projects.stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getById(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + id));
        log.info("Getting employee by id: {}", id);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO getByName(String name) {
        Project project = projectRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "Name: " + name));
        log.info("Getting project with Name: {}", name);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO delete(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + id));
        for (Employee employee : project.getEmployeeList()) {
            employee.setProject(null);
        }
        projectRepo.delete(project);
        log.info("Deleted project with id: {}", id);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO update(ProjectDTO project) {
        Project existingProject = projectRepo.findById(project.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + project.getId()));
        existingProject.setName(project.getName());
        Project updatedProject = projectRepo.save(existingProject);
        log.info("Updated Project : {}", updatedProject);
        return ProjectMapper.toDTO(updatedProject);
    }

}
