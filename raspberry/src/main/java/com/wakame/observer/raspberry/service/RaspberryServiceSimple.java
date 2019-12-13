package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.domain.sampling.Photograph;
import com.wakame.observer.raspberry.domain.sampling.Sampler;
import com.wakame.observer.raspberry.infrastructure.messaging.slack.SlackMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class RaspberryServiceSimple implements RaspberryService {

    @Autowired
    Sampler sampling;

    @Autowired
    SlackMessageSender slackMessageSender;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws JsonProcessingException {

        try {
            sampling.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() throws IOException {
        while(true) {
            Photograph photograph = sampling.take();
            photograph.storeTo(Paths.get("C:\\Users\\y-nakata\\Desktop"));
            slackMessageSender.post();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
