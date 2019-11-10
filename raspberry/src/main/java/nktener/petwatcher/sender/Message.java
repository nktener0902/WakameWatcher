package nktener.petwatcher.sender;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;

public class Message extends AWSIotMessage {
    public Message(String topic, AWSIotQos qos, String payload) {
        super(topic, qos, payload);
    }

	@Inject
	transient Logger log;

    @Override
    public void onSuccess() {
        // called when message publishing succeeded
    	log.info(System.currentTimeMillis() + ": >>> " + getStringPayload());
    }

    @Override
    public void onFailure() {
        // called when message publishing failed
    	log.severe(System.currentTimeMillis() + ": publish failed for " + getStringPayload());
    }

    @Override
    public void onTimeout() {
        // called when message publishing timed out
    	log.severe(System.currentTimeMillis() + ": publish timeout for " + getStringPayload());
    }
}
