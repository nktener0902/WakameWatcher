package com.wakame.observer.raspberry.domain.messaging;

import com.wakame.observer.raspberry.AppConfig;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SlackMessageSenderImpl implements SlackMessageSender {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private HttpSender httpSender;

    private WebhookUri webhookUri;
    private Token token;
    private Channel channel;

    @Override
    public void post(Photograph photograph) throws Exception {

        webhookUri = WebhookUri.createWebhook(appConfig.getSlackWebhookUrl());
        token = Token.createToken(appConfig.getSlackWebhookToken());
        channel = Channel.createChannel(appConfig.getSlackWebhookChannel());

        log.debug("webhookUri=" + webhookUri.toString());
        log.debug("token=" + token.toString());
        log.debug("channel=" + channel.toString());
        log.debug("imagePath=" + photograph.getImage().getAbsolutePath());

        if(!photograph.includesCat()) {
           log.info("Your cat is out now.");
           Map<String, String> forms = new HashMap<>();
           forms.put("token", token.toString());
           forms.put("channel", channel.toString());
           forms.put("text", "Your cat is out now.");
           httpSender.postText("https://slack.com/api/chat.postMessage", forms);
        }

        //TODO: If a cat is in the given photograph, send the photo to a slack channel.
        //      Otherwise, send a message to a slack channel.

        Map<String, String> forms = new HashMap<>();
        forms.put("token", token.toString());
        forms.put("channel", channel.toString());
        forms.put("imagePath", "@"+photograph.getImage().getAbsolutePath());

        httpSender.postImage(webhookUri.toString(), forms);

    }

}
