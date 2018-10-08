package com.wakame.observer.raspberry.model.sampling.sensing;

public interface Sensing {

    void init();

    SensingData sense();

}
