package com.charitan.management.project

import ace.charitan.common.dto.payment.CancelHaltedProjectSubscriptionResponseDto
import com.charitan.management.project.dto.ProjectHaltReasonDto

interface ProjectManagementExternalService {
    suspend fun halt(
        projectId: String,
        reasonDto: ProjectHaltReasonDto,
    )

    suspend fun approve(projectId: String)

    suspend fun getDonationsByProjectId(projectId: String): CancelHaltedProjectSubscriptionResponseDto
}
