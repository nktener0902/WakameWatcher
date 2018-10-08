package com.wakame.observer.raspberry.infrastructure.messaging.awsiot;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.wakame.observer.raspberry.model.messaging.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageImpl extends AWSIotMessage implements Message {

    public MessageImpl(String topic, AWSIotQos qos, String payload) {
        super(topic, qos, payload);
    }

    Log log = LogFactory.getLog(MessageImpl.class);

    @Override
    public void onSuccess() {
        // called when message publishing succeeded
    	log.info(System.currentTimeMillis() + ": >>> " + getStringPayload());
    }

    @Override
    public void onFailure() {
        // called when message publishing failed
    	log.error(System.currentTimeMillis() + ": publish failed for " + getStringPayload());
    }

    @Override
    public void onTimeout() {
        // called when message publishing timed out
    	log.error(System.currentTimeMillis() + ": publish timeout for " + getStringPayload());
    }
}
