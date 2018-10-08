package com.wakame.observer.raspberry.model.messaging;

public interface Sending {

    void init(String[] CommandArgs) throws Exception;

    void send();
}
