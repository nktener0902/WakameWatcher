package com.wakame.observer.raspberry.infrastructure.messaging.slack;

public interface SlackMessageSender {

    void post() throws Exception;

}
