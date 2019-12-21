package com.wakame.observer.raspberry.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public String getSlackWebhookUrl() throws IOException {
        log.info(this.slackWebhookUrl);
        List<String> lines = Files.readAllLines(Paths.get(this.slackWebhookUrl), StandardCharsets.UTF_8);
        log.info(lines.get(0));
        return lines.get(0);
    }

}
