package com.charitan.management.project.internal

import ace.charitan.common.dto.donation.GetDonationsByProjectIdDto
import ace.charitan.common.dto.email.project.EmailProjectHaltDonorDto
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
        producerService.sendReplying(ProjectHaltDto(projectId, reasonDto.charity, reasonDto.donor))
        val donations = getDonationsByProjectId(projectId)

        producerService.send(
            donations.donations.map { donation ->
                EmailProjectHaltDonorDto(
                    donation.donorId,
                    "Donation cancellation",
                    "Your donation has been cancelled. Reason: ${donation.message}",
                )
                // TODO: add notification
            },
        )

        // TODO: add charity email
    }

    override suspend fun approve(projectId: String) {
        val response = producerService.sendReplying(ProjectApproveDto(projectId))
    }

    override suspend fun getDonationsByProjectId(projectId: String) = producerService.sendReplying(GetDonationsByProjectIdDto(projectId))
}
