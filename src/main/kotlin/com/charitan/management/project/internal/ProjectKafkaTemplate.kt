package com.charitan.management.project.internal

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
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
    ): ConcurrentMessageListenerContainer<String, Any> =
            containerFactory.createContainer(
                "project.management.replies",
            ).apply {
                containerProperties.setGroupId("management-service")
                isAutoStartup = true
            }

    @Bean
    fun repliesTopics() =
        KafkaAdmin.NewTopics(
            TopicBuilder
                .name("project.management.replies")
                .partitions(3)
                .replicas(2)
                .build(),
        )
}
