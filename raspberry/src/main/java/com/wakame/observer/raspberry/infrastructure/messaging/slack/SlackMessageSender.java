package com.wakame.observer.raspberry.infrastructure.messaging.slack;

import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;

public interface SlackMessageSender {

    void post(Photograph photograph) throws Exception;

}
