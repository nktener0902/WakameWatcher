package com.wakame.observer.raspberry.model.messaging;

public interface Sender {

    void init(String[] CommandArgs) throws Exception;

    void send();
}
