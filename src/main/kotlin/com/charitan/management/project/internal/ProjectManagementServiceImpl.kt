package com.charitan.management.project.internal

import ace.charitan.common.dto.email.project.EmailProjectHaltCharityDto
import ace.charitan.common.dto.email.project.EmailProjectHaltDonorDto
import ace.charitan.common.dto.notification.payment.HaltedProjectDonorNotificationRequestDto
import ace.charitan.common.dto.payment.CancelHaltedProjectSubscriptionRequestDto
import ace.charitan.common.dto.project.ProjectApproveDto
import ace.charitan.common.dto.project.ProjectHaltDto
import com.charitan.management.project.ProjectManagementExternalService
import com.charitan.management.project.dto.ProjectHaltReasonDto
import org.springframework.stereotype.Service

@Service
internal class ProjectManagementServiceImpl(
    private val producerService: ProjectProducerService,
) : ProjectManagementInternalService,
    ProjectManagementExternalService {
    override suspend fun halt(
        projectId: String,
        reasonDto: ProjectHaltReasonDto,
    ) {
        val project = producerService.sendReplying(ProjectHaltDto(projectId, reasonDto.charity, reasonDto.donor))
        val donations = getDonationsByProjectId(projectId)

        producerService.send(
            donations.donorIds.map { donation ->
                EmailProjectHaltDonorDto(
                    donation,
                    "Donation cancellation",
                    "Your monthly donation to ${project.title} has been cancelled. Reason: ${reasonDto.donor}",
                )
            },
        )
        producerService.send(
            HaltedProjectDonorNotificationRequestDto(
                donations.donorIds,
                project,
            ),
        )

        producerService.send(
            EmailProjectHaltCharityDto(
                project.charityId,
                "Project Halted",
                "Your project ${project.title} has been halted. Reason: ${reasonDto.charity}",
            ),
        )
    }

    override suspend fun approve(projectId: String) {
        val response = producerService.sendReplying(ProjectApproveDto(projectId))
    }

    override suspend fun getDonationsByProjectId(projectId: String) =
        producerService.sendReplying(
            CancelHaltedProjectSubscriptionRequestDto(projectId),
        )
}
