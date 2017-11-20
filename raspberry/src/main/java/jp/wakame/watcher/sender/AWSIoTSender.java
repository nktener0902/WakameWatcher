package jp.wakame.watcher.sender;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;

import jp.wakame.watcher.util.CommandArguments;
import jp.wakame.watcher.util.CustomUtil;
import jp.wakame.watcher.util.CustomUtil.KeyStorePasswordPair;

public class AWSIoTSender implements ISender, Serializable{

	private AWSIotMqttClient awsIotClient;
	private String topicName;
	private AWSIotQos qos = AWSIotQos.QOS0;

	@Inject
	transient Logger log;

	@Override
	public void init(String[] CommandArgs){
		CommandArguments arguments = CommandArguments.parse(CommandArgs);
        initClient(arguments);
        log.info("Initialized AWS IoT client");

        /** AWS IoTを接続 **/
        try {
        	awsIotClient.connect();
        	log.info("Success connecting to your Thing of AWS IoT");
        } catch (AWSIotException e){
        	log.severe("Failedd connecting to your Thing of AWS IoT");
        	log.severe(e.getMessage());
        	return;
        }
	}

	private void initClient(CommandArguments arguments) {
		this.topicName = arguments.get("topicName", CustomUtil.getConfig("topicName"));

		String clientEndpoint = arguments.getNotNull("clientEndpoint", CustomUtil.getConfig("clientEndpoint"));
		String clientId = arguments.getNotNull("clientId", CustomUtil.getConfig("clientId"));

		String certificateFile = arguments.get("certificateFile", CustomUtil.getConfig("certificateFile"));
		String privateKeyFile = arguments.get("privateKeyFile", CustomUtil.getConfig("privateKeyFile"));
		if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
			String algorithm = arguments.get("keyAlgorithm", CustomUtil.getConfig("keyAlgorithm"));

			KeyStorePasswordPair pair = CustomUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);

			awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
		}

		if (awsIotClient == null) {
			String awsAccessKeyId = arguments.get("awsAccessKeyId", CustomUtil.getConfig("awsAccessKeyId"));
			String awsSecretAccessKey = arguments.get("awsSecretAccessKey", SampleUtil.getConfig("awsSecretAccessKey"));
			String sessionToken = arguments.get("sessionToken", CustomUtil.getConfig("sessionToken"));

			if (awsAccessKeyId != null && awsSecretAccessKey != null) {
				awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
						sessionToken);
			}
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

		Message message = new Message(topicName, qos, payload);
		try {
			awsIotClient.publish(message, timeout);
			log.info("Sent message to AWS IoT");
		} catch (AWSIotException e) {
			log.severe("Cannot publish msg :" + payload);
			e.printStackTrace();
		}
	}


}
