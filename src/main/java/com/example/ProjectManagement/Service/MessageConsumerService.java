//package com.example.ProjectManagement.Service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageConsumerService {
//
//    private final JwtService jwtService;
//
//    public MessageConsumerService(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
//
//    @KafkaListener(topics = "onSigKeyUpdate", groupId = "key-management-group")
//    public void consumeKeyUpdateMessage(String message) {
//        try {
//            // Parse the public key from the message
//            String publicKey = parsePublicKey(message);
//
//            // Update the JWTService with the new public key
//            jwtService.updatePublicKey(publicKey);
//            System.out.println("Public key updated in JwtService.");
//        } catch (Exception e) {
//            System.err.println("Failed to process key update: " + e.getMessage());
//        }
//    }
//
//    private String parsePublicKey(String message) throws JsonProcessingException {
//        // Parse JSON message (example: {"publicKey": "keyData"})
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(message);
//        return jsonNode.get("publicKey").asText();
//    }
//}
