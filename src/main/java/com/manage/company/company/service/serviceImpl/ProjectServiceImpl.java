package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.exeption.EntityAlreadyExistException;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.ProjectMapper;
import com.manage.company.company.model.Project;
import com.manage.company.company.repository.ProjectRepo;
import com.manage.company.company.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public ProjectDTO save(Project project) {
        Optional<Project> existingProject = projectRepo.findByName(project.getName());
        if (existingProject.isPresent()) {
            throw new EntityAlreadyExistException("Project", project.getName() + " already exists.");
        }

        Project savedProject = projectRepo.save(project);
        log.info("Saved Project with ID: {}", savedProject.getId());
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
        log.info("Found Project with ID: {}", id);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO getByName(String name) {
        Project project = projectRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "Name: " + name));
        log.info("Found Project with Name: {}", name);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO delete(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + id));
        projectRepo.delete(project);
        log.info("Deleted Project with ID: {}", id);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO update(Project project) {
        Project existingProject = projectRepo.findById(project.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + project.getId()));
        existingProject.setName(project.getName());
        Project updatedProject = projectRepo.save(existingProject);
        log.info("Updated Project with ID: {}", project.getId());
        return ProjectMapper.toDTO(updatedProject);
    }

}
