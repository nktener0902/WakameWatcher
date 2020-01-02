package com.wakame.observer.raspberry.domain.messaging;

public interface Sender {

    void init(String[] CommandArgs) throws Exception;

    void send();

    void stop() throws SenderException;
}
