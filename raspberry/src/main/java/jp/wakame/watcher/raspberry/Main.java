package jp.wakame.watcher.raspberry;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.amazonaws.services.iot.client.AWSIotException;

import jp.wakame.watcher.sender.ISender;

public class Main {

	public static void main(String[] args) {

		String[] CommandArgs = args;
		Weld weld = new Weld();
		try (WeldContainer container = weld.initialize()) {
			Main bean = container.select(Main.class).get();
			try {
				bean.run(CommandArgs);
			} catch (AWSIotException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Inject
	WebCamera webcam;

	@Inject
	ISender mqtt;

	@Inject
	transient Logger log;

	public void run(String[] CommandArgs) throws InterruptedException, AWSIotException {

		/** AWS IoTへのコネクションを初期化 **/
		mqtt.init(CommandArgs);

		while (true) {
			/** 画像取得 **/
			log.info("Take photo");
			try {
				webcam.takePhoto();
			} catch (IOException e) {
				log.severe("Failed to get a photo from Rasperry Pi. Retry after 5 minutes.");
				Thread.sleep(5 * 60 * 1000);
				continue;
			}

			/** 画像ディレクトリ数を100以下にする **/
			try {
				if (webcam.oldPhotoRemove()){
					log.info("Removed old photos");
				}
			} catch (IOException | NullPointerException e1) {
				log.severe("Cannot delete photo");
				e1.printStackTrace();
			}

			/** TODO MQTTでデータ送信 **/
			mqtt.send();

			/** 1分スリープ **/
			Thread.sleep(5 * 1000);
		}
	}
}
