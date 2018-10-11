package com.wakame.observer.raspberry.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wakame.observer.raspberry.service.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/observer")
public class ObserverController {

    @Autowired
    RaspberryService raspberryService;

    @RequestMapping(value="start", method=RequestMethod.GET)
    public String start() {
        String responseJson = null;
        try {
            responseJson = raspberryService.init();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Internal Error. See logs(./raspberry/logs/raspberry.log).\n";
        }
        return responseJson;
    }

    @RequestMapping(value="status", method=RequestMethod.GET)
    public String status() {
        String responseJson = null;
        try {
            responseJson = raspberryService.status();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Internal Error. See logs(./raspberry/logs/raspberry.log).\n";
        }
        return responseJson;
    }

    @RequestMapping(value="stop", method=RequestMethod.GET)
    public String stop() {
        String responseJson = null;
        try {
            responseJson = raspberryService.stop();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Internal Error\n";
        }
        return responseJson;
    }
}
