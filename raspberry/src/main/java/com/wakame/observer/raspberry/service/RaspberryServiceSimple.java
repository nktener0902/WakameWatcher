package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.domain.config.AppConfig;
import com.wakame.observer.raspberry.domain.sampling.Sampler;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;
import com.wakame.observer.raspberry.infrastructure.slack.SlackMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaspberryServiceSimple implements RaspberryService {

    private Sampler sampler;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SlackMessageSender slackMessageSender;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws JsonProcessingException {

        this.sampler = Sampler.createSampler();

    }

    @Override
    public void start() throws Exception {
        while(true) {
            Photograph photograph = sampler.take();
            slackMessageSender.post(photograph);
            try {
                Thread.sleep(appConfig.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
