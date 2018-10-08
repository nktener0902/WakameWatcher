package com.wakame.observer.raspberry.model.sampling.sensing;

import com.wakame.observer.raspberry.model.messaging.Messaging;

public interface Sensing {

    void init(Messaging messaging);

    void stop();
}
