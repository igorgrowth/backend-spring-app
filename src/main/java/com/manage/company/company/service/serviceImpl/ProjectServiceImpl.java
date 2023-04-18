package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.entity.Project;
import com.manage.company.company.exeption.EntityAlreadyExistException;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.ProjectMapper;
import com.manage.company.company.entity.Employee;
import com.manage.company.company.repository.EmployeeRepo;
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
    private final EmployeeRepo employeeRepo;

    public ProjectServiceImpl(ProjectRepo projectRepo, EmployeeRepo employeeRepo) {
        this.projectRepo = projectRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {

            projectDTO.setEmployeeList(Collections.emptyList());
        log.info("Save project");
        Project project = ProjectMapper.toEntity(projectDTO);
        Optional<Project> existingProject = projectRepo.findByName(project.getName());
        if (existingProject.isPresent()) {
            throw new EntityAlreadyExistException("Project", project.getName() + " already exists.");
        }

        Project savedProject = projectRepo.save(project);
        log.info("Saved project with id: {}", savedProject.getId());
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

    @Override
    public ProjectDTO addEmployeeToProject(Long projectId, Long employeeId) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project ", "id: " + projectId));

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "id: " + employeeId));
        project.getEmployeeList().add(employee);
        employee.setProject(project);
        Project updatedProject = projectRepo.save(project);
        Employee updatedEmployee = employeeRepo.save(employee);
        log.info("Add employee {} in Project : {}", updatedEmployee, updatedProject);
        return ProjectMapper.toDTO(updatedProject);
    }

}
