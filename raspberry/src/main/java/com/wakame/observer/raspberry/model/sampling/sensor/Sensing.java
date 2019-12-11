package com.wakame.observer.raspberry.model.sampling.sensor;

public interface Sensing {

    void init();

    SensedData sense();

}
