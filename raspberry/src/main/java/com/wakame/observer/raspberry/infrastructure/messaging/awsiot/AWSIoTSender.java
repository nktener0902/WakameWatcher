package com.wakame.observer.raspberry.infrastructure.messaging.awsiot;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.wakame.observer.raspberry.model.messaging.Sending;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class AWSIoTSender implements Sending, Serializable{

	private AWSIotMqttClient awsIotClient;
	private String topicName;
	private AWSIotQos qos = AWSIotQos.QOS0;

	transient Logger log;

	@Override
	public void init(String[] CommandArgs) throws AWSIotException {
		CommandArguments arguments = CommandArguments.parse(CommandArgs);
		initClient(arguments);
        log.info("Initialized AWS IoT client");

        /** AWS IoTを接続 **/
        awsIotClient.connect();
        log.info("Success connecting to your Thing of AWS IoT");
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
			log.info("Sent message to AWS IoT");
		} catch (AWSIotException e) {
			log.severe("Cannot publish msg :" + payload);
			e.printStackTrace();
		}
	}


}
