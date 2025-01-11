package com.charitan.management.project

import com.charitan.management.project.dto.ProjectHaltReasonDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/admin/project")
@RestController
class ProjectManagementController(
    private val projectManagementService: ProjectManagementExternalService,
) {
    @PostMapping("/halt/{id}")
    suspend fun haltProject(
        @PathVariable id: String,
        @RequestBody dto: ProjectHaltReasonDto,
    ) {
        projectManagementService.halt(id, dto)
    }

    @PostMapping("/approve/{id}")
    suspend fun approveProject(
        @PathVariable id: String,
    ) {
        projectManagementService.approve(id)
    }
}
