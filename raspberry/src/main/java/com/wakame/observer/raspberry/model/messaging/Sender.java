package com.wakame.observer.raspberry.model.messaging;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotTimeoutException;

public interface Sender {

    void init(String[] CommandArgs) throws Exception;

    void send();

    void stop() throws SenderException;
}
