package com.charitan.management.project.internal

import com.charitan.management.project.dto.ProjectHaltDto
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

@Service
internal class ProjectProducerService(
    private val replyingTemplate: ReplyingKafkaTemplate<String, Any, Any>,
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private suspend fun send(
        topic: ProjectProducerTopic,
        data: Any,
    ): ConsumerRecord<String, Any> {
        val record: ProducerRecord<String, Any> = ProducerRecord(topic.name, data)
        val request = replyingTemplate.sendAndReceive(record)

        val send = request.sendFuture.await()
        logger.info("Sent replying future request to $topic, metadata ${send.recordMetadata}")

        val result = request.await()
        logger.info("Request to $topic has been replied, value size ${result.serializedValueSize()}")

        return result
    }

    suspend fun send(projectHaltDto: ProjectHaltDto) = send(ProjectProducerTopic.PROJECT_HALT, projectHaltDto)

    @Bean
    fun keyTopics() =
        KafkaAdmin.NewTopics(
            *ProjectProducerTopic.entries
                .map { topic ->
                    TopicBuilder
                        .name(topic.name)
                        .partitions(3)
                        .replicas(2)
                        .build()
                }.toTypedArray(),
            TopicBuilder
                .name("replies-container")
                .partitions(3)
                .replicas(2)
                .build(),
        )
}
