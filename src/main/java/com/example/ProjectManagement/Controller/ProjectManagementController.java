package com.example.ProjectManagement.Controller;


import com.example.ProjectManagement.DTO.ProjectDto;
import com.example.ProjectManagement.Service.ProjectManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectManagementController {

    private final ProjectManagementService projectManagementService;

    public ProjectManagementController(ProjectManagementService projectManagementService) {
        this.projectManagementService = projectManagementService;
    }

    @PostMapping
    public String createProject(@RequestBody ProjectDto projectDto) {
        return projectManagementService.createProject(projectDto);
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@PathVariable String id) {
        return projectManagementService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public String updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto) {
        return projectManagementService.updateProject(id, projectDto);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable String id) {
        return projectManagementService.deleteProject(id);
    }

    @GetMapping // Supports GET
    public ResponseEntity<?> getProjects() {
        // Handle the request
        return ResponseEntity.ok().body("Projects Retrieved");
    }
}
