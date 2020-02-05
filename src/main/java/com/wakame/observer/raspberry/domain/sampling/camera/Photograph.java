package com.wakame.observer.raspberry.domain.sampling.camera;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Photograph {

    private File image;
    private final Path path;

    static public Photograph createPhoto(File file) {
        return new Photograph(file);
    }

    static public Photograph createPhoto(BufferedImage bi) throws IOException {
        File directory = new File("./tmp");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("A directory was created successfully");
            } else {
                throw new IOException("Cannot create new directory that is for storing pictures");
            }
        }
        try {
            ImageIO.write(bi, "PNG", new File("./tmp/photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Photograph(new File("tmp/photo.png"));
    }

    private Photograph(File file) {
        this.image = file;
        this.path = file.toPath();
    }

    public Path getPath() {
        return this.path;
    }

    public File getImage() {return this.image; }

    public boolean includesCat() throws IOException {

        RekognitionClient rekognition = RekognitionClient.builder()
                .build();

        DetectLabelsResponse detectLabelsResponse =
                rekognition.detectLabels(
                        DetectLabelsRequest.builder().image(
                                Image.builder().bytes(
                                        SdkBytes.fromByteArray(Files.readAllBytes(image.toPath())))
                                        .build()).build());

        List<String> labels = detectLabelsResponse.labels().stream()
                .map((label) -> label.name())
                .collect(Collectors.toList());

        return labels.contains("Cat");
    }

}
