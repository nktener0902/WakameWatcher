package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RaspberryService {
    int STOPPED = 0;
    int ONLINE = 1;
    String init() throws JsonProcessingException;
    String status() throws JsonProcessingException;
}
