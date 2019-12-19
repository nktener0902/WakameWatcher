package com.wakame.observer.raspberry.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${slack.webhook.url:/tmp/slack/webhook/url}")
    private String slackWebhookUrl;

    public String getSlackWebhookUrlString(){
        return this.slackWebhookUrl;
    }

    public String getSlackWebhookUrl(){
        return this.slackWebhookUrl;
    }
}
