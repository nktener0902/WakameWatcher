package com.wakame.observer.raspberry.domain.sampling;

public class Sampler {

    private Camera camera;

    static public Sampler createSampler(){
        return new Sampler(Camera.createCamera());
    }

    private Sampler(Camera camera){

        this.camera = camera;

    }

    public Photograph take() {
        return camera.takePhoto();
    }
}
