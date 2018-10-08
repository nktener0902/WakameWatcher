package com.wakame.observer.raspberry.model.sampling.sensing.impl;

import com.wakame.observer.raspberry.model.sampling.sensing.Sensing;
import com.wakame.observer.raspberry.model.sampling.sensing.SensingData;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SensingImpl implements Sensing {

    @Override
    public void init() {
        // Todo: 気温が取得可能な状態にする
        // Todo: 湿度が取得可能な状態にする
    }

    @Override
    public SensingData sense() {
        /** 現在時刻を取得 **/
        Date date = new Date();
        String currentData = date.toString();
        // Todo: 気温を取得
        // Todo: 湿度を取得
        return null;
    }

}
