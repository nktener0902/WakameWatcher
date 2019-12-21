package com.wakame.observer.raspberry.domain.sampling.camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CameraOnMac implements Camera {

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
        final List<String> execPath = Arrays.asList("imagesnap -q -w 1 ./tmp/photo.png".split(" "));
        try {
            Process proc = new ProcessBuilder(execPath).start();
            proc.waitFor();
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        BufferedImage bufferedImage = ImageIO.read(new File("./tmp/photo.png"));
        return Photograph.createPhoto(bufferedImage);
    }
}
