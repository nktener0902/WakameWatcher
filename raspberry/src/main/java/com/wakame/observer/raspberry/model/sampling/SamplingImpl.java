package com.wakame.observer.raspberry.model.sampling;

import com.wakame.observer.raspberry.model.sampling.Sampling;
import com.wakame.observer.raspberry.model.sampling.camera.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SamplingImpl implements Sampling {

    @Autowired
    private Camera camera;

    @Override
    public void init() throws Exception {

    }

    @Override
    public Photograph take() {
        return camera.takePhoto();
    }
}
