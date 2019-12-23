package com.wakame.observer.raspberry.domain.sampling.camera;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CameraOnLinux implements Camera {

    @Override
    public Photograph takePhoto() throws IOException {
        File directory = new File("./tmp");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("フォルダの作成に成功しました");
            } else {
                throw new IOException("Cannot create new directory to store pictures");
            }
        }
        final List<String> execPath = Arrays.asList("fswebcam -r 1920x1080 -F 100 ./tmp/photo.png".split(" "));
        try {
            Process proc = new ProcessBuilder(execPath).start();
            InputStream in = proc.getInputStream();
            proc.waitFor();
            in.close();
            proc.getOutputStream().close();
            proc.getErrorStream().close();
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return Photograph.createPhoto(new File("tmp/photo.png"));
    }

}
