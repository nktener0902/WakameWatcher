package com.wakame.observer.raspberry.model.sampling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Photograph {

    private BufferedImage image;

    static public Photograph createPhoto(BufferedImage bufferedImage) {
        return new Photograph(bufferedImage);
    }

    private Photograph(BufferedImage bufferedImage) {
        this.image = bufferedImage;
    }

    public void storeTo(Path path) throws IOException {
        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = sdf.format(cl.getTime()) + ".png";
        ImageIO.write(image, "png", new File(path.toString() + "\\" + fileName));
    }

}
