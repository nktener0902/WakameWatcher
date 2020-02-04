package com.wakame.observer.raspberry.domain.messaging;

import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;

public interface SlackMessageSender {

    void post(Photograph photograph) throws Exception;

}
