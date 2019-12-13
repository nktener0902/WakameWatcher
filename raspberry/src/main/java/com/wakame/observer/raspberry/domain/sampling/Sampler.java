package com.wakame.observer.raspberry.domain.sampling;

public class Sampler {

    private Camera camera;

    static public Sampler createSampler(){
        return new Sampler();
    }

    public Sampler(){

        this.camera = Camera

    }

    public Photograph take() {
        return camera.takePhoto();
    }
}
