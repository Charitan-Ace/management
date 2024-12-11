package com.example.ProjectManagement.Service;

import com.example.ProjectManagement.DTO.ProjectDto;

public interface ProjectManagementServiceInterface {

    String createProject(ProjectDto projectDto);

    ProjectDto getProjectById(String id);

    String updateProject(String id, ProjectDto projectDto);

    String deleteProject(String id);
}
