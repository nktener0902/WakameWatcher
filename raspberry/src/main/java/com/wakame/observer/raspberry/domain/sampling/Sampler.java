package com.wakame.observer.raspberry.domain.sampling;

import com.wakame.observer.raspberry.domain.sampling.camera.Camera;
import com.wakame.observer.raspberry.domain.sampling.camera.Photograph;

import java.io.IOException;

public class Sampler {

    private Camera camera;

    static public Sampler createSampler(){
        return new Sampler(Camera.createCamera());
    }

    private Sampler(Camera camera){

        this.camera = camera;

    }

    public Photograph take() throws IOException {
        return camera.takePhoto();
    }
}
