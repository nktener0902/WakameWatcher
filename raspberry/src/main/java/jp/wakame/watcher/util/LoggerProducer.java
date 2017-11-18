package jp.wakame.watcher.util;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

@Dependent
public class LoggerProducer {

	@Inject
	InjectionPoint point;

	@Produces
	private Logger createLogger(){
		Logger lg = Logger.getLogger(point.getMember().getDeclaringClass().getName());
		return lg;
	}

}
