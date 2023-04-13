package com.manage.company.company.controller;

import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.model.Project;
import com.manage.company.company.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${url}/project")
public class ProjectController {

        @Autowired
        private ProjectService projectService;

        public ProjectController(ProjectService projectService) {
            this.projectService = projectService;
        }

        @GetMapping
        public ResponseEntity<List<ProjectDTO>> getProjects() {
            return ResponseEntity.ok().body(projectService.findAll());
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
            return new ResponseEntity<>(projectService.save(project), HttpStatus.CREATED);
        }


        @PutMapping
        public ResponseEntity<ProjectDTO> updateProject(@RequestBody Project project) {
            return ResponseEntity.ok().body(projectService.update(project));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
            return ResponseEntity.ok().body(projectService.getById(id));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ProjectDTO> deleteProject(@PathVariable Long id) {
            return ResponseEntity.ok().body(projectService.delete(id));
        }

}
