package com.charitan.management.project.internal

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate

@Configuration
class ProjectKafkaTemplate {
    @Bean
    fun replyingTemplate(
        producerFactory: ProducerFactory<String, Any>,
        repliesContainer: ConcurrentMessageListenerContainer<String, Any>,
    ): ReplyingKafkaTemplate<String, Any, Any> = ReplyingKafkaTemplate(producerFactory, repliesContainer)

    @Bean
    fun repliesContainer(
        containerFactory: ConcurrentKafkaListenerContainerFactory<String, Any>,
    ): ConcurrentMessageListenerContainer<String, Any> {
        val repliesContainer =
            containerFactory.createContainer(
                "replies-container",
            )
        repliesContainer.containerProperties.setGroupId("management-replies-service")
        repliesContainer.isAutoStartup = false
        return repliesContainer
    }
}
