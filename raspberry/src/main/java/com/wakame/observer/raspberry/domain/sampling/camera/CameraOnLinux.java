package com.wakame.observer.raspberry.domain.sampling.camera;

import com.github.sarxos.webcam.Webcam;

import java.util.List;

public class CameraOnLinux implements Camera {

    @Override
    public Photograph takePhoto() {
        Webcam webcam = null;
        List<Webcam> webcams = Webcam.getWebcams();
        System.out.println(webcams.size());
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
