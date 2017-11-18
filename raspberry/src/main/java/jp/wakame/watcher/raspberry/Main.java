package jp.wakame.watcher.raspberry;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {
	public static String PHOTODIR = "/home/y-nakata/dev/wakame_system/photos/"; // 末尾に/(スラッシュ)を忘れないこと

	public static void main(String[] args) throws InterruptedException, MqttException {

		Weld weld = new Weld();
		try (WeldContainer container = weld.initialize()) {
			RequestContext requestContext = container.select(RequestContext.class, UnboundLiteral.INSTANCE).get();
			requestContext.activate();
			Main bean = container.select(Main.class).get();
			bean.run();
		}
	}

	@Inject
	WebCamera webcam;

	@Inject
	MqttBean mqtt;

	@Inject
	transient Logger log;

	public void run() throws InterruptedException {

		/** ロガー設定 **/
		try {
			Handler handler = new FileHandler("my.log", 500000, 2);
			log.addHandler(handler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		this.log.setLevel(Level.INFO);

		while (true) {
			/** 画像取得 **/
			webcam.takePhoto();
			/** 画像ディレクトリ数を100以下にする **/
			webcam.oldPhotoRemove();

			/** MQTTでデータ送信 **/
			/** 画像を送信 **/
			/** AWS SDKを利用 **/
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			this.log.info("Message published");

			/** 15分スリープ **/
			// Thread.sleep(15 * 60 * 1000);
			Thread.sleep(15 * 1000);
		}

	}
}
