package com.charitan.management.project.internal

import ace.charitan.common.dto.project.ProjectApproveDto
import ace.charitan.common.dto.project.ProjectHaltDto
import com.charitan.management.project.ProjectManagementExternalService
import org.springframework.stereotype.Service

@Service
internal class ProjectManagementServiceImpl(
    private val producerService: ProjectProducerService,
) : ProjectManagementInternalService,
    ProjectManagementExternalService {
    override suspend fun halt(projectId: String) {
        val response = producerService.send(ProjectHaltDto(projectId))
    }

    override suspend fun approve(projectId: String) {
        val response = producerService.send(ProjectApproveDto(projectId))
    }
}
