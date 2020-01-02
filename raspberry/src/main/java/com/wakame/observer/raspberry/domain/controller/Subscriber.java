package com.wakame.observer.raspberry.domain.controller;

import com.wakame.observer.raspberry.domain.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Subscriber {

    @Autowired
    private AppConfig appConfig;

    public List<PhotoOrderMessage> getMessages(){

        log.info("Access AWS SQS");

        String queueName = appConfig.getQueueName();
        SqsClient sqsClient = SqsClient.builder().region(appConfig.getSqsRegion()).build();

        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();
        String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .build();
        List<Message> messages= sqsClient.receiveMessage(receiveMessageRequest).messages();

        var messagesList = new ArrayList<PhotoOrderMessage>();

        for (Message message : messages) {
            log.info(message.body());
            messagesList.add(new PhotoOrderMessage(message.body()));
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);
        }

        sqsClient.close();

        return messagesList;
    }
}
