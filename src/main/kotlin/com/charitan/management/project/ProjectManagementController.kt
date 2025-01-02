package com.charitan.management.project

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
    ) {
        projectManagementService.halt(id)
    }

    @PostMapping("/approve/{id}")
    suspend fun approveProject(
        @PathVariable id: String,
    ) {
        projectManagementService.approve(id)
    }
}
