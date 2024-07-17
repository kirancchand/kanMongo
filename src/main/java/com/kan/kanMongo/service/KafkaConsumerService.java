package com.kan.kanMongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class KafkaConsumerService {
	
    @Autowired
    private UserMService userMService;
    
    @KafkaListener(topics="UserRegistration",groupId="kafkaMongoGroupId")
    public void consume(String message) throws JsonMappingException, JsonProcessingException {
        System.out.println("Consumed message from kanMongo"+message);
        userMService.ReqFromKafka(message);
    }

}
