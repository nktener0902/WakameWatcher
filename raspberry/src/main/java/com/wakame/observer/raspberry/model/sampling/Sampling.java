package com.wakame.observer.raspberry.model.sampling;

public interface Sampling {
    void init() throws Exception;
    void start();
    void stop();
}
