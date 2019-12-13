package com.wakame.observer.raspberry.domain.sampling.sensor;

public interface Sensing {

    void init();

    SensedData sense();

}
