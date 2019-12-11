package com.wakame.observer.raspberry.model.sampling.sensor.impl;

import com.wakame.observer.raspberry.model.sampling.sensor.Sensing;
import com.wakame.observer.raspberry.model.sampling.sensor.SensedData;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

@Component
public class SensingDHT11 implements Sensing {

    @Autowired
    protected ResourceLoader resourceLoader;

    @Override
    public void init() {
        // Todo: 気温が取得可能な状態にする
        // Todo: 湿度が取得可能な状態にする
    }

    @Override
    public SensedData sense() {
        /** 現在時刻を取得 **/
        Date date = new Date();
        String currentData = date.toString();
        // Todo: 気温を取得
        // Todo: 湿度を取得
        Properties props = new Properties();

        props.put("python.path", "【上のsample.pyのあるディレクトリ】");
        props.put("python.console.encoding", "UTF-8");

        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
        try (PythonInterpreter interp = new PythonInterpreter()) {
            interp.exec("import sense_temperature_humid");
            interp.exec("temperature, humidity = sense_temperature_humid.sensing('11', 4)");
        }

        return null;
    }

}
