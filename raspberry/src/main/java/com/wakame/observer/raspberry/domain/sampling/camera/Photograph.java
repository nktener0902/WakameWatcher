package com.wakame.observer.raspberry.domain.sampling.camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Photograph {

    private File image;
    private final Path path;

    static public Photograph createPhoto(File file) {
        return new Photograph(file);
    }

    static public Photograph createPhoto(BufferedImage bi) {
        try {
            ImageIO.write(bi, "PNG", new File("./photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Photograph(new File("./photo.png"));
    }

    private Photograph(File file) {
        this.image = file;
        this.path = file.toPath();
    }

    public Path getPath() {
        return this.path;
    }

    public File getImage() {return this.image; }

}
