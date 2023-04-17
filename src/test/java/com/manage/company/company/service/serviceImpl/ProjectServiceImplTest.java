package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.model.Project;
import com.manage.company.company.repository.ProjectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepo projectRepo;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private ProjectDTO projectDTO;
    private Project project;

    @BeforeEach
    void setUp() {
        projectDTO =
                ProjectDTO.builder()
                        .id(1L)
                        .name("test name")
                        .employeeList(Collections.emptyList())
                        .build();
        project =
                Project.builder()
                        .id(1L)
                        .name("test name")
                        .employeeList(Collections.emptyList())
                        .build();
    }


    @Test
    void save_ShouldSaveAndReturnProjectDTO() {
        //When
        when(projectRepo.save(any(Project.class))).thenReturn(project);

        ProjectDTO savedProjectDTO = projectService.save(projectDTO);

        // Assert
        verify(projectRepo, times(1)).save(project);
        assertEquals(projectDTO.getId(), savedProjectDTO.getId());
        assertEquals(projectDTO.getName(), savedProjectDTO.getName());
        assertEquals(projectDTO.getEmployeeList(), savedProjectDTO.getEmployeeList());

    }

    @Test
    void findAll_ShouldReturnListOfProjectDTO() {
        //Setup
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        projects.add(new Project(2L, "Project 2", Collections.emptyList()));

        //When
        when(projectRepo.findAll()).thenReturn(projects);

        List<ProjectDTO> resultProjectDTOList = projectService.findAll();

        // Assert
        verify(projectRepo, times(1)).findAll();
        assertEquals(projects.size(), resultProjectDTOList.size());
        assertEquals(resultProjectDTOList.get(0).getId(), projectDTO.getId());
        assertEquals(resultProjectDTOList.get(0).getName(), projectDTO.getName());
        assertEquals(resultProjectDTOList.get(0).getEmployeeList(), projectDTO.getEmployeeList());
    }

    @Test
    void getById_ShouldReturnProjectDTO() {
        //When
        when(projectRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(project));

        ProjectDTO resultProjectDTO = projectService.getById(1L);

        // Assert
        verify(projectRepo, times(1)).findById(1L);
        assertEquals(project.getId(), resultProjectDTO.getId());
        assertEquals(project.getName(), resultProjectDTO.getName());
        assertEquals(project.getEmployeeList(), resultProjectDTO.getEmployeeList());
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException() {
        //Setup
        Long id = 999L;

        //When
        when(projectRepo.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> projectService.getById(id));
    }

    @Test
    void getByName_ShouldReturnProjectDTO() {
        //Setup
        String testName = "test name";
        //When
        when(projectRepo.findByName(testName)).thenReturn(Optional.of(project));

        ProjectDTO resultProjectDTO = projectService.getByName(testName);

        // Assert
        assertNotNull(resultProjectDTO);
        assertEquals(project.getId(), resultProjectDTO.getId());
        assertEquals(project.getName(), resultProjectDTO.getName());
    }

    @Test
    void getByName_ShouldThrowResourceNotFoundException() {
        //Setup
        String name = "NonExistingProject";
        //When
        when(projectRepo.findByName(anyString())).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> projectService.getByName(name));
    }

    @Test
    void update_ShouldUpdateProject() {
        //Setup
        ProjectDTO updatedProjectDTO =  new ProjectDTO(1L, "Updated Project", Collections.emptyList());

        //When
        when(projectRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(project));
        when(projectRepo.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectService.update(updatedProjectDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProjectDTO.getId(), result.getId());
        assertEquals(updatedProjectDTO.getName(), result.getName());
    }

    @Test
    void delete_ShouldDeleteProject() {
        //When
        when(projectRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(project));

        ProjectDTO result = projectService.delete(1L);

        //Assert
        assertNotNull(result);
        assertEquals(project.getId(), result.getId());
        assertEquals(project.getName(), result.getName());
        verify(projectRepo, times(1)).delete(project);
    }
}