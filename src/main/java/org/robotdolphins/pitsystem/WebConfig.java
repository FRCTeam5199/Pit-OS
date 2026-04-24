package org.robotdolphins.pitsystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.images-dir:assets/image-cache}")
    private String imagesDir;

    @Value("${app.preloaded-images-dir:assets/preloaded-images}")
    private String preloadedImagesDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String cachePath = Paths.get(System.getProperty("user.dir"))
                .resolve(imagesDir).toAbsolutePath().toString().replace("\\", "/");
        String preloadedPath = Paths.get(System.getProperty("user.dir"))
                .resolve(preloadedImagesDir).toAbsolutePath().toString().replace("\\", "/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + cachePath + "/", "file:" + preloadedPath + "/")
                .setCachePeriod(0);
    }
}
