package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.domain.config.AppConfig;
import com.wakame.observer.raspberry.domain.adapter.Subscriber;
import com.wakame.observer.raspberry.domain.sampling.Sampler;
import com.wakame.observer.raspberry.domain.messaging.SlackMessageSender;
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
        long start = 0;
        long end = 0;
        while(true) {
            slackMessageSender.post(sampler.take());
            start = System.currentTimeMillis();
            end = System.currentTimeMillis();
            while((end - start) < appConfig.getInterval()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (subscriber.getMessages().isEmpty()){
                    log.info("No photo request");
                } else {
                    log.info("Received a photo request");
                    slackMessageSender.post(sampler.take());
                }
                end = System.currentTimeMillis();
            }
        }
    }
}
