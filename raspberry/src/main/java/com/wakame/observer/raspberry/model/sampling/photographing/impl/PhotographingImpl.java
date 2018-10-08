package com.wakame.observer.raspberry.model.sampling.photographing.impl;

import com.wakame.observer.raspberry.model.sampling.photographing.Photograph;
import com.wakame.observer.raspberry.model.sampling.photographing.Photographing;

public class PhotographingImpl implements Photographing {

    public Photograph takePhoto() {
        return new Photograph();
    }
}
