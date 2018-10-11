package com.wakame.observer.raspberry.infrastructure.messaging.awsiot;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTimeoutException;
import com.wakame.observer.raspberry.model.messaging.Sender;
import com.wakame.observer.raspberry.model.messaging.SenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AWSIoTSender implements Sender, Serializable {

    private AWSIotMqttClient awsIotClient;
    private String topicName;
    private AWSIotQos qos = AWSIotQos.QOS0;

    private static final Logger logger = LoggerFactory.getLogger(AWSIoTSender.class);

    @Override
    public void init(String[] CommandArgs) throws AWSIotException {
        CommandArguments arguments = CommandArguments.parse(CommandArgs);
        initClient(arguments);
        logger.info("Initialized AWS IoT client");
        awsIotClient.connect();
        logger.info("Success connecting to your Thing of AWS IoT");
    }

    private void initClient(CommandArguments arguments) {
        this.topicName = arguments.get("topicName", CustomUtil.getConfig("topicName"));

        String clientEndpoint = arguments.getNotNull("clientEndpoint", CustomUtil.getConfig("clientEndpoint"));
        String clientId = arguments.getNotNull("clientId", CustomUtil.getConfig("clientId"));
        String certificateFile = arguments.get("certificateFile", CustomUtil.getConfig("certificateFile"));
        String privateKeyFile = arguments.get("privateKeyFile", CustomUtil.getConfig("privateKeyFile"));

        if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
            String algorithm = arguments.get("keyAlgorithm", CustomUtil.getConfig("keyAlgorithm"));

            CustomUtil.KeyStorePasswordPair pair = CustomUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);

            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }

        if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
    }

    @Override
    public void send() {
        Date d = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String payload = d1.format(d);
        long timeout = 3000;                    // milliseconds

        MessageImpl message = new MessageImpl(topicName, qos, payload);
        try {
            awsIotClient.publish(message, timeout);
            logger.info("Sent message to AWS IoT");
        } catch (AWSIotException e) {
            logger.error("Cannot publish msg :" + payload);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws SenderException {
        try {
            awsIotClient.disconnect(5000L);
            logger.info("Success disconnecting to your Thing of AWS IoT");
            awsIotClient = null;
        } catch (AWSIotException | AWSIotTimeoutException e) {
            e.printStackTrace();
            throw new SenderException("Fail");
        }
    }


}
