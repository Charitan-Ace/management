package com.charitan.management.project.internal

import ace.charitan.common.dto.donation.DonationsDto
import ace.charitan.common.dto.donation.GetDonationsByProjectIdDto
import ace.charitan.common.dto.email.project.EmailProjectHaltCharityDto
import ace.charitan.common.dto.email.project.EmailProjectHaltDonorDto
import ace.charitan.common.dto.notification.payment.HaltedProjectDonorNotificationRequestDto
import ace.charitan.common.dto.payment.CancelHaltedProjectSubscriptionRequestDto
import ace.charitan.common.dto.payment.CancelHaltedProjectSubscriptionResponseDto
import ace.charitan.common.dto.project.ExternalProjectDto
import ace.charitan.common.dto.project.ProjectApproveDto
import ace.charitan.common.dto.project.ProjectHaltDto
import kotlinx.coroutines.future.await
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
internal class ProjectProducerService(
    private val template: ReplyingKafkaTemplate<String, Any, Any>,
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private fun send(
        topic: ProjectProducerTopic,
        data: Any,
    ) {
        template.send(topic.topic, data)
    }

    private suspend fun sendReplying(
        topic: ProjectProducerTopic,
        data: Any,
    ): Any {
        if (!template.waitForAssignment(Duration.ofSeconds(10))) {
            error("Template container hasn't been initialize")
        }

        val record: ProducerRecord<String, Any> = ProducerRecord(topic.topic, data)
        val request = template.sendAndReceive(record, Duration.ofSeconds(10))

        val send = request.sendFuture.await()
        logger.info("Sent replying future request to ${topic.topic}, metadata ${send.recordMetadata}")

        val result = request.await()
        logger.info("Request to $topic has been replied, value size ${result.serializedValueSize()}")

        return result.value()
    }

    suspend fun sendReplying(projectHaltDto: ProjectHaltDto) =
        sendReplying(ProjectProducerTopic.PROJECT_HALT, projectHaltDto) as ExternalProjectDto


    suspend fun sendReplying(projectApproveDto: ProjectApproveDto) = sendReplying(ProjectProducerTopic.PROJECT_APPROVE, projectApproveDto)

    suspend fun sendReplying(getDonationsByProjectIdDto: GetDonationsByProjectIdDto) =
        sendReplying(ProjectProducerTopic.DONATION_GET_BY_PROJECT, getDonationsByProjectIdDto) as DonationsDto

    suspend fun sendReplying(cancelHaltedProjectSubscriptionRequestDto: CancelHaltedProjectSubscriptionRequestDto) =
        sendReplying(ProjectProducerTopic.PAYMENT_CANCEL_HALTED_PROJECT_SUBSCRIPTIONS, cancelHaltedProjectSubscriptionRequestDto) as CancelHaltedProjectSubscriptionResponseDto

    suspend fun send(emailProjectHaltDonorDto: EmailProjectHaltDonorDto) =
        send(ProjectProducerTopic.EMAIL_PROJECT_HALT_DONOR, emailProjectHaltDonorDto)

    suspend fun send(emailProjectHaltCharityDto: EmailProjectHaltCharityDto) =
        send(ProjectProducerTopic.EMAIL_PROJECT_HALT_CHARITY, emailProjectHaltCharityDto)

    suspend fun send(emailProjectHaltDonorDtos: List<EmailProjectHaltDonorDto>) =
        send(ProjectProducerTopic.EMAIL_PROJECT_HALT_DONOR, emailProjectHaltDonorDtos)

    suspend fun send(haltedProjectDonorNotificationRequestDto: HaltedProjectDonorNotificationRequestDto) =
        send(ProjectProducerTopic.NOTIFICATION_HALTED_PROJECT, haltedProjectDonorNotificationRequestDto)
}
