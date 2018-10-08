package com.wakame.observer.raspberry.controller;

import com.wakame.observer.raspberry.model.messaging.Sending;
import com.wakame.observer.raspberry.model.sampling.sensing.Sensing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/observer")
public class ObserverController {

    @Autowired
    private Sending sending;

    @Autowired
    private Sensing sensing;

    @RequestMapping(value="start", method=RequestMethod.GET)
    public void start() {

        String[] CommandArgs = {
                "-clientEndpoint", "a3w46o5kz2kt1t-ats.iot.ap-northeast-1.amazonaws.com",
                "-clientId", "sdk-java",
                "-certificateFile", "../wakamepicture.cert.pem",
                "-privateKeyFile", "../wakamepicture.private.key"
        };
        try {
            sending.init(CommandArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
