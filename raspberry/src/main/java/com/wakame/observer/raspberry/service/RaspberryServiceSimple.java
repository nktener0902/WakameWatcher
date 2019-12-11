package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.model.messaging.Sender;
import com.wakame.observer.raspberry.model.messaging.SenderException;
import com.wakame.observer.raspberry.model.sampling.Photograph;
import com.wakame.observer.raspberry.model.sampling.Sampling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class RaspberryServiceSimple implements RaspberryService {

    @Autowired
    Sender sender;

    @Autowired
    Sampling sampling;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws JsonProcessingException {

        String[] AwsIotCommandArgs = {
                "-clientEndpoint", "a3w46o5kz2kt1t-ats.iot.ap-northeast-1.amazonaws.com",
                "-clientId", "sdk-java",
                "-certificateFile", "../wakamepicture.cert.pem",
                "-privateKeyFile", "../wakamepicture.private.key"
        };

        try {
            sender.init(AwsIotCommandArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
