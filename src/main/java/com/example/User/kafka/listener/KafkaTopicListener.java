package com.example.User.kafka.listener;

import com.example.User.helper.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicListener {

    @Autowired
    private UserUtility userUtility;

    @KafkaListener(topics = "File",containerFactory = "UserKafkaListenerContainerFactory")
    public void listener(@Payload String message){
            userUtility.processExcelFile(message);
        System.err.println("received message from File" );
    }
}
