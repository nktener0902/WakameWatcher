package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import com.wakame.observer.raspberry.domain.config.AppConfig;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Slf4j
@Component
public class SlackMessageSenderImpl implements SlackMessageSender {

    @Autowired
    private AppConfig appConfig;

    private WebhookUri webhookUri;
    private Token token;
    private Channel channel;

    @Override
    public void post(Photograph photograph) throws Exception {

        webhookUri = WebhookUri.createWebhook(appConfig.getSlackWebhookUrl());
        token = Token.createToken(appConfig.getSlackWebhookToken());
        channel = Channel.createChannel(appConfig.getSlackWebhookChannel());

        Path imagePath = photograph.getPath();

        log.info("webhookUri=" + webhookUri.toString());
        log.info("token=" + token.toString());
        log.info("channel=" + channel.toString());
        log.info("imagePath=" + imagePath.toString());

        HttpResponse<String> response = Unirest.post(webhookUri.toString())
                .header("Content-Type", "multipart/form-data")
                //.header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + token.toString())
                .field("token", token.toString())
                .field("channels", channel.toString())
                .field("file", photograph.getImage())
                .asString();

        log.info(response.getBody());

    }

}
