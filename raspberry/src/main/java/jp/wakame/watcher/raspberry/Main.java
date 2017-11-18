package jp.wakame.watcher.raspberry;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;

import jp.wakame.watcher.util.CommandArguments;
import jp.wakame.watcher.util.SampleUtil;
import jp.wakame.watcher.util.SampleUtil.KeyStorePasswordPair;

public class Main {
	public static String PHOTODIR = "/home/y-nakata/dev/wakame_system/photos/"; // 末尾に/(スラッシュ)を忘れないこと

    private static final String TestTopic = "sdk/test/java";
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

	public void run() throws InterruptedException, AWSIotException {

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

			CommandArguments arguments = CommandArguments.parse(CommandArgs);
	        initClient(arguments);

	        awsIotClient.connect();

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
