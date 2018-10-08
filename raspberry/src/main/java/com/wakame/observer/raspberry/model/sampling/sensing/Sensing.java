package com.wakame.observer.raspberry.model.sampling.sensing;

import com.wakame.observer.raspberry.model.messaging.Sending;

public interface Sensing {

    void init();

    SensingData sense();

    void stop();
}
