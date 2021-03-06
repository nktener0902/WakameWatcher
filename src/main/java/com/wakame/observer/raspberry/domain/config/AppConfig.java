package com.wakame.observer.raspberry.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class AppConfig {

    @Value("${slack.webhook.url:/tmp/slack/webhook/url}")
    private String slackWebhookUrl;

    @Value("${slack.webhook.token:/tmp/slack/webhook/token}")
    private String slackWebhookToken;

    @Value("${slack.webhook.channel:/tmp/slack/webhook/channel}")
    private String slackWebhookChannel;

    @Value("${interval}")
    private String interval;

    @Value("${aws.sqs.queuename}")
    private String queueName;

    @Value("${aws.sqs.region}")
    private String sqsRegion;

    public String getSlackWebhookUrl() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.slackWebhookUrl), StandardCharsets.UTF_8);
        return lines.get(0);
    }

    public String getSlackWebhookToken() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.slackWebhookToken), StandardCharsets.UTF_8);
        return lines.get(0);
    }

    public String getSlackWebhookChannel() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.slackWebhookChannel), StandardCharsets.UTF_8);
        return lines.get(0);
    }

    public Integer getInterval(){
        return Integer.parseInt(interval);
    }

    public String getQueueName() {return this.queueName; }

    public Region getSqsRegion() { return Region.of(this.sqsRegion); }

}
