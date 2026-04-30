package org.robotdolphins.pitsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ImageController {

    @Value("${app.images-dir:assets/image-cache}")
    private String imagesDir;

    @Value("${app.preloaded-images-dir:assets/preloaded-images}")
    private String preloadedImagesDir;

    private static final String IMAGE_PATTERN = "(?i).*\\.(jpg|jpeg|png|gif|webp)";

    @GetMapping("/api/images")
    public List<String> getImages() throws IOException {
        return loadImages();
    }

    @PostMapping("/api/images/reset")
    public ResponseEntity<String> resetImages() {
        Path dir = Paths.get(System.getProperty("user.dir")).resolve(imagesDir);
        File[] files = dir.toFile().listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().matches(IMAGE_PATTERN)) f.delete();
            }
        }
        return ResponseEntity.ok("Cache cleared");
    }

    private List<String> loadImages() throws IOException {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        collectNames(Paths.get(System.getProperty("user.dir")).resolve(imagesDir), names);
        collectNames(Paths.get(System.getProperty("user.dir")).resolve(preloadedImagesDir), names);
        return names.stream()
                .map(n -> "/images/" + n)
                .sorted()
                .collect(Collectors.toList());
    }

    private void collectNames(Path dir, LinkedHashSet<String> names) {
        File[] files = dir.toFile().listFiles();
        if (files == null) return;
        Arrays.stream(files)
                .filter(f -> f.getName().matches(IMAGE_PATTERN))
                .map(File::getName)
                .forEach(names::add);
    }
}
