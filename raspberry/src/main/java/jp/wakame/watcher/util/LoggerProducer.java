package jp.wakame.watcher.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

@Dependent
public class LoggerProducer {

	@Inject
	InjectionPoint point;

	@Inject
	@Formatter
	private SimpleFormatter customFormatter;

	@Produces
	private Logger createLogger() {
		Logger lg = Logger.getLogger(point.getMember().getDeclaringClass().getName());

		/** ロガー設定 **/
		/* ファイル出力 */
		try {
			Handler fileOutHandler = new FileHandler("log/log.xml", 5000000, 2);
			fileOutHandler.setFormatter(customFormatter);
			fileOutHandler.setLevel(Level.ALL);
			lg.addHandler(fileOutHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		/* 標準出力 */
		Handler consoleOutHandler = new StreamHandler();
		consoleOutHandler.setFormatter(new CustomLogFormatter());
		lg.addHandler(consoleOutHandler);

		/* ロガーのレベルを設定 */
		lg.setLevel(Level.INFO);

		return lg;
	}

}
