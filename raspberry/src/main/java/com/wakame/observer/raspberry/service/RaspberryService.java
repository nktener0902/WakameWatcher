package com.wakame.observer.raspberry.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface RaspberryService {
    int STOPPED = 0;
    int ONLINE = 1;

    void init() throws JsonProcessingException;

    void start() throws IOException;

}
