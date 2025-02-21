package com.example.User.kafka.producer;

import com.example.User.kafka.constant.Constants;
import com.example.User.utils.Logger;
import com.example.User.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducerSend {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void whenComplete(String message, CompletableFuture<SendResult<String,String>> future,String topicNamelog){
        future.whenComplete(((stringStringSendResult, throwable) -> {
            if (Objects.isNull(throwable)){
                System.out.println("sent "+topicNamelog+" Log Message ");
            }else{
                System.out.println("Unable To  sent "+topicNamelog+" Log Message "+ throwable);
            }
        }));
    }

    public void sendFile(MultipartFile file) throws IOException {

        byte[] fileByte = file.getBytes();

        String message = Base64.encodeBase64String(fileByte);

        System.out.println("sending file Message ");
        CompletableFuture<SendResult<String,String>> future= this.kafkaTemplate.send("File",Utils.generateKafkaTopicKey("File"),message);

        this.whenComplete(message,future,"File");
    }

    public void sendCommonAPILogMessage(String message, String requestID) {
        Logger.info("Sending Common API Log Message With Key : " + message);

        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate
                .send(Constants.KAFKA_TOPIC_COMMON_API_LOGS, requestID /*Utils.generateKafkaTopicKey(Constants
                        .KAFKA_TOPIC_ELASTIC_SEARCH_ACTIVITY_LOGS)*/, message);
        this.whenComplete(message, future, Constants.KAFKA_TOPIC_COMMON_API_LOGS);
    }

    public void removeExpiredTokenFromDataBase(String message, String requestID) {
        Logger.info("Sending Common API Log Message With Key : " + message);
        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate
                .send(Constants.KAFKA_TOPIC_REMOVE_TOKEN_FROM_REDIS_AUTH_TABLE, requestID, message);
        this.whenComplete(message, future, Constants.KAFKA_TOPIC_REMOVE_TOKEN_FROM_REDIS_AUTH_TABLE);
    }
}
