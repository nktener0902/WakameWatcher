package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.domain.config.AppConfig;
import com.wakame.observer.raspberry.domain.controller.Subscriber;
import com.wakame.observer.raspberry.domain.sampling.Sampler;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import com.wakame.observer.raspberry.infrastructure.slack.SlackMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RaspberryServiceSimple implements RaspberryService {

    private Sampler sampler;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SlackMessageSender slackMessageSender;

    @Autowired
    private Subscriber subscriber;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws JsonProcessingException {

        this.sampler = Sampler.createSampler();

    }

    @Override
    public void start() throws Exception {
        subscriber.getMessages();
        long start = 0;
        long end = 0;
        while(true) {
            log.info("Go on a next interval");
            start = System.currentTimeMillis();
            Photograph photograph = sampler.take();
            //slackMessageSender.post(photograph);
            end = System.currentTimeMillis();
            while((end - start) < appConfig.getInterval()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!subscriber.getMessages().isEmpty()){
                    log.info("Received a photo request");
                    photograph = sampler.take();
                    slackMessageSender.post(photograph);
                } else {
                    log.info("No photo request");
                }
                end = System.currentTimeMillis();
            }
        }
    }
}
