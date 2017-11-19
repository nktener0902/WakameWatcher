package jp.wakame.watcher.raspberry;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.inject.Inject;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;

import jp.wakame.watcher.mqtt.MqttBean;
import jp.wakame.watcher.util.CommandArguments;
import jp.wakame.watcher.util.CustomLogFormatter;
import jp.wakame.watcher.util.Formatter;
import jp.wakame.watcher.util.SampleUtil;
import jp.wakame.watcher.util.SampleUtil.KeyStorePasswordPair;

public class Main {

	private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0;
	private static AWSIotMqttClient awsIotClient;
	private static String[] CommandArgs;

	public static void main(String[] args) throws InterruptedException, AWSIotException {

		CommandArgs = args;
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

	@Inject @Formatter
	private SimpleFormatter customFormatter;

	public void run() throws InterruptedException, AWSIotException {

		/** ロガー設定 **/
		try {
			/* ファイル出力 */
			Handler fileOutHandler = new FileHandler("log/log.xml", 5000000, 2);
			fileOutHandler.setFormatter(customFormatter);
			fileOutHandler.setLevel(Level.ALL);
			log.addHandler(fileOutHandler);
			/* 標準出力 */
			Handler consoleOutHandler = new StreamHandler();
			consoleOutHandler.setFormatter(new CustomLogFormatter());
			log.addHandler(consoleOutHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		this.log.setLevel(Level.INFO);

		while (true) {
			/** 画像取得 **/
			log.info("Take photo");
			try {
				webcam.takePhoto();
			} catch (IOException e) {
				log.severe("Failed to get a photo from Rasperry Pi");
				Thread.sleep(5 * 60 * 1000);
				continue;
			}

			/** 画像ディレクトリ数を100以下にする **/
			try {
				if (webcam.oldPhotoRemove()){
					log.info("Removed old photos");
				}
			} catch (IOException e1) {
				log.severe("Cannot delete photo");
				e1.printStackTrace();
			}

			/** MQTTでデータ送信 **/
			/** 画像を送信 **/
			/** AWS SDKを利用 **/

			CommandArguments arguments = CommandArguments.parse(CommandArgs);
	        initClient(arguments);
	        log.info("Initialized MQTT client");

	        /** AWS IoTを接続 **/
	        try {
	        	awsIotClient.connect();
	        	log.info("Success connecting to your Thing of AWS IoT");
	        } catch (AWSIotException e){
	        	log.severe("Failedd connecting to your Thing of AWS IoT");
	        	log.severe(e.getMessage());
	        	return;
	        }


			/** 1分スリープ **/
			Thread.sleep(60 * 1000);
		}

	}

	private static void initClient(CommandArguments arguments) {
		String clientEndpoint = arguments.getNotNull("clientEndpoint", SampleUtil.getConfig("clientEndpoint"));
		String clientId = arguments.getNotNull("clientId", SampleUtil.getConfig("clientId"));

		String certificateFile = arguments.get("certificateFile", SampleUtil.getConfig("certificateFile"));
		String privateKeyFile = arguments.get("privateKeyFile", SampleUtil.getConfig("privateKeyFile"));
		if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
			String algorithm = arguments.get("keyAlgorithm", SampleUtil.getConfig("keyAlgorithm"));

			KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);

			awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
		}

		if (awsIotClient == null) {
			String awsAccessKeyId = arguments.get("awsAccessKeyId", SampleUtil.getConfig("awsAccessKeyId"));
			String awsSecretAccessKey = arguments.get("awsSecretAccessKey", SampleUtil.getConfig("awsSecretAccessKey"));
			String sessionToken = arguments.get("sessionToken", SampleUtil.getConfig("sessionToken"));

			if (awsAccessKeyId != null && awsSecretAccessKey != null) {
				awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
						sessionToken);
			}
		}

		if (awsIotClient == null) {
			throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
		}
	}
}
