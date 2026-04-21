package org.robotdolphins.pitsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ImageController {

    @Value("${app.images-dir:src/main/resources/static/images}")
    private String imagesDir;

    @GetMapping("/api/images")
    public List<String> getImages() throws IOException {
        return loadImages();
    }

    private List<String> loadImages() throws IOException {
        Path dir = findImagesDir();
        File[] files = dir.toFile().listFiles();
        if (files == null) return Collections.emptyList();
        return Arrays.stream(files)
                .filter(f -> f.getName().matches("(?i).*\\.(jpg|jpeg|png|gif|webp)"))
                .map(f -> "/images/" + f.getName())
                .sorted()
                .collect(Collectors.toList());
    }

    private Path findImagesDir() throws IOException {
        // working directory relative (standard bootRun from project root)
        Path workdir = Paths.get(System.getProperty("user.dir")).resolve(imagesDir);
        if (Files.isDirectory(workdir)) return workdir;

        // classpath (DevTools adds src/main/resources to classpath)
        ClassPathResource cpr = new ClassPathResource("static/images/");
        if (cpr.exists()) return cpr.getFile().toPath();

        throw new IOException("Images directory not found. Tried: " + workdir);
    }
}
