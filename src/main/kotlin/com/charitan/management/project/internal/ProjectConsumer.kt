package com.charitan.management.project.internal

import com.charitan.management.project.dto.ProjectHaltDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
internal class ProjectConsumer {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = ["project.halt"], id = "mock-service")
    fun haltTopicListener(dto: ProjectHaltDto): String {
        logger.info("Mock service received request for halting ${dto.projectId}")
        return "success"
    }
}
