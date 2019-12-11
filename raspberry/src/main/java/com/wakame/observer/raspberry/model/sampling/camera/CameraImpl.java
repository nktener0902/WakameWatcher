package com.wakame.observer.raspberry.model.sampling.camera;

import com.github.sarxos.webcam.Webcam;
import com.wakame.observer.raspberry.model.sampling.Photograph;
import org.springframework.stereotype.Component;

@Component
public class CameraImpl implements Camera {

    public Photograph takePhoto() {
        Webcam webcam = null;
        webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam : " + webcam.getName());
            webcam.open();
            return Photograph.createPhoto(webcam.getImage());
        } else {
            throw new ExceptionInInitializerError("Cannot found camera");
        }
    }
}
