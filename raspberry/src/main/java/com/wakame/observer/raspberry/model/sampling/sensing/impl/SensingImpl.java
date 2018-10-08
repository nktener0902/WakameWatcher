package com.wakame.observer.raspberry.model.sampling.sensing.impl;

import com.wakame.observer.raspberry.model.messaging.Messaging;
import com.wakame.observer.raspberry.model.sampling.sensing.Sensing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class SensingImpl implements Sensing {

    @Autowired
    private Messaging messaging;

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    private void sensing(){
        /** 現在時刻を取得 **/
        Date date = new Date();
        String currentData = date.toString();
    }

    @Override
    public void init(Messaging messaging) {

    }

    @Override
    public void stop() {

    }
}
