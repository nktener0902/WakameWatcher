package jp.wakame.observer.raspberry;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {
	public static String PHOTODIR = "/home/y-nakata/dev/wakame_system/photos/"; // 末尾に/(スラッシュ)を忘れないこと

	public static void main(String[] args) throws InterruptedException, MqttException {

		String topic = "wakame";
		String content = "Message from MqttPublishSample";
		int qos = 2;
		String broker = "tcp://iot.eclipse.org:1883";
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();


		MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		System.out.println("Connecting to broker: " + broker);
		sampleClient.connect(connOpts);
		System.out.println("Connected");
		System.out.println("Publishing message: " + content);


		/** 画像クラス **/
		WebCamera webcam = new WebCamera(PHOTODIR);

		while (true) {
			/** 画像取得 **/
			webcam.takePhoto();
			/** 画像ディレクトリ数を100以下にする **/
			webcam.oldPhotoRemove();

			/** MQTTでデータ送信 **/
			/** 画像を送信 **/
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");

			/** 15分スリープ **/
			// Thread.sleep(15 * 60 * 1000);
			Thread.sleep(15 * 1000);
		}
	}
}
