package jp.wakame.watcher.mqtt;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@SessionScoped
public class MqttBean implements Serializable{
	String topic = "wakame";
	String content = "Message from MqttPublishSample";
	int qos = 2;
	String broker = "tcp://iot.eclipse.org:1883";
	String clientId = "JavaSample";
	MemoryPersistence persistence = new MemoryPersistence();

	void setTopic(String topic){
		this.topic = topic;
	}

	@Inject
	Logger log;

	public MqttBean() throws MqttException{

		MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		this.log.info("Connecting to broker: " + broker);
		sampleClient.connect(connOpts);
		this.log.info("Connected");
		this.log.info("Publishing message: " + content);
	}
}
