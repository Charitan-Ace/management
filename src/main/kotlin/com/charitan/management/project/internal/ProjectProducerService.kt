package com.charitan.management.project.internal

import ace.charitan.common.dto.project.ProjectApproveDto
import ace.charitan.common.dto.project.ProjectHaltDto
import kotlinx.coroutines.future.await
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
internal class ProjectProducerService(
    private val replyingTemplate: ReplyingKafkaTemplate<String, Any, Any>,
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private suspend fun send(
        topic: ProjectProducerTopic,
        data: Any,
    ): ConsumerRecord<String, Any> {
        if (!replyingTemplate.waitForAssignment(Duration.ofSeconds(10))) {
            error("Template container hasn't been initialize")
        }

        val record: ProducerRecord<String, Any> = ProducerRecord(topic.topic, data)
        val request = replyingTemplate.sendAndReceive(record, Duration.ofSeconds(10))

        val send = request.sendFuture.await()
        logger.info("Sent replying future request to ${topic.topic}, metadata ${send.recordMetadata}")

        val result = request.await()
        logger.info("Request to $topic has been replied, value size ${result.serializedValueSize()}")

        return result
    }

    suspend fun send(projectHaltDto: ProjectHaltDto) = send(ProjectProducerTopic.PROJECT_HALT, projectHaltDto)

    suspend fun send(projectApproveDto: ProjectApproveDto) = send(ProjectProducerTopic.PROJECT_APPROVE, projectApproveDto)

    @Bean
    fun managementTopics() =
        KafkaAdmin.NewTopics(
            *ProjectProducerTopic.entries
                .map { topic ->
                    TopicBuilder
                        .name(topic.topic)
                        .partitions(3)
                        .replicas(2)
                        .build()
                }.toTypedArray(),
        )
}
