//package com.example.ProjectManagement.Service;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageProducerService {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Value("${kafka.topic.status-change}")
//    private String statusChangeTopic;
//
//    @Value("${kafka.topic.status-change-notify}")
//    private String statusChangeNotifyTopic;
//
//    public MessageProducerService(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    /**
//     * Sends a message to the status change topic with a key.
//     *
//     * @param projectId The ID of the project (used as the key).
//     * @param status    The new status of the project.
//     */
//    public void sendStatusChangeMessage(String projectId, String status) {
//        String message = String.format("Project ID: %s, Status: %s", projectId, status);
//        kafkaTemplate.send(statusChangeTopic, projectId, message);
//        System.out.println("Sent message to topic " + statusChangeTopic + " with key " + projectId + ": " + message);
//    }
//
//    /**
//     * Sends a notification message to the notification topic with a key.
//     *
//     * @param projectId The ID of the project (used as the key).
//     * @param message   The notification message to be sent.
//     */
//    public void sendStatusChangeNotification(String projectId, String message) {
//        String notificationMessage = String.format("Project ID: %s, Notification: %s", projectId, message);
//        kafkaTemplate.send(statusChangeNotifyTopic, projectId, notificationMessage);
//        System.out.println("Sent message to topic " + statusChangeNotifyTopic + " with key " + projectId + ": " + notificationMessage);
//    }
//}
