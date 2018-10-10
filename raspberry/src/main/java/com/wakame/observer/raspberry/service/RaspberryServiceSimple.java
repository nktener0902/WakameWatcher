package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakame.observer.raspberry.model.messaging.Sender;
import com.wakame.observer.raspberry.model.sampling.Sampling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RaspberryServiceSimple implements RaspberryService{

    private int status;

    @Autowired
    Sender sender;

    @Autowired
    Sampling sampling;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String init() throws JsonProcessingException {

        if (status == ONLINE){
            return objectMapper.writeValueAsString(new Response(this.status));
        }

        String[] CommandArgs = {
                "-clientEndpoint", "a3w46o5kz2kt1t-ats.iot.ap-northeast-1.amazonaws.com",
                "-clientId", "sdk-java",
                "-certificateFile", "../wakamepicture.cert.pem",
                "-privateKeyFile", "../wakamepicture.private.key"
        };

        try {
            sender.init(CommandArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Response(this.status));
        }

        try {
            sampling.init();
        } catch (Exception e) {
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Response(this.status));
        }

        status = ONLINE;

        return objectMapper.writeValueAsString(new Response(this.status));

    }

    @Override
    public String status() throws JsonProcessingException {
        return objectMapper.writeValueAsString(new Response(this.status));
    }

    private class Response {

        public String status;

        @JsonCreator
        private Response(@JsonProperty("status") int s){
            if (s == RaspberryService.ONLINE) {
                this.status = "ONLINE";
            }
            if (s == RaspberryService.STOPPED){
                this.status = "STOPPED";
            }
        }

        @Override
        public String toString() {
            return "Response [status=" + this.status + "]";
        }
    }
}
