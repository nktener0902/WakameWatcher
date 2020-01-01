package com.wakame.observer.raspberry.infrastructure.slack;

import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;

public interface SlackMessageSender {

    void post(Photograph photograph) throws Exception;

}
