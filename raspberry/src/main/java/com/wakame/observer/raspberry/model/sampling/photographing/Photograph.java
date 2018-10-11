package com.wakame.observer.raspberry.model.sampling.photographing;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

public class Photograph {

    private BufferedImage image;

    public Photograph() {
        /** 写真を撮る **/
        Webcam webcam = null;
        webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam : " + webcam.getName());
            webcam.open();
            this.image = webcam.getImage();
        }
    }
}
