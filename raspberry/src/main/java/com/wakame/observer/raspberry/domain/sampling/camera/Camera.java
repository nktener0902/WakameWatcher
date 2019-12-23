package com.wakame.observer.raspberry.domain.sampling.camera;

import java.io.IOException;

public interface Camera {

    public static Camera createCamera(){
        final String OS_NAME = System.getProperty("os.name").toLowerCase();
        switch (OS_NAME) {
            case "linux":
                return new CameraOnLinux();
            case "mac os x":
                return new CameraOnMac();
            case "windows":
                return new CameraOnLinux();
            default:
                throw new RuntimeException("Unsupported OS: " + OS_NAME);
        }
    }

    Photograph takePhoto() throws IOException;

}
