package com.charitan.management.project.internal

import com.charitan.management.project.ProjectManagementExternalService
import com.charitan.management.project.dto.ProjectHaltDto
import org.springframework.stereotype.Service

@Service
internal class ProjectManagementServiceImpl(
    private val producerService: ProjectProducerService,
) : ProjectManagementInternalService,
    ProjectManagementExternalService {
    override suspend fun halt(projectId: String) {
        val response = producerService.send(ProjectHaltDto(projectId))
    }
}
