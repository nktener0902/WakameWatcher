package com.wakame.observer.raspberry.domain.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Log4j
@Controller
public class Subscriber {

    String queueName = "queue" + System.currentTimeMillis();
    SqsClient sqsClient = SqsClient.builder().region(Region.US_WEST_2).build();

    // snippet-start:[sqs.java2.sqs_example.create_queue]
    CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueName).build();
        sqsClient.createQueue(createQueueRequest);
    // snippet-end:[sqs.java2.sqs_example.create_queue]

        System.out.println("\nGet queue url");
    // snippet-start:[sqs.java2.sqs_example.get_queue]
    GetQueueUrlResponse getQueueUrlResponse =
            sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
    String queueUrl = getQueueUrlResponse.queueUrl();
        System.out.println(queueUrl);
    // snippet-end:[sqs.java2.sqs_example.get_queue]

    public PhotoOrderMessage incoming(){
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .build();
        List<Message> messages= sqsClient.receiveMessage(receiveMessageRequest).messages();
        return new PhotoOrderMessage();
    }
}
