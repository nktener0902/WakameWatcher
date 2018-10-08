package com.wakame.observer.raspberry.model.messaging;

public interface Messaging {

    void init(String[] CommandArgs) throws Exception;

    void send();
}
