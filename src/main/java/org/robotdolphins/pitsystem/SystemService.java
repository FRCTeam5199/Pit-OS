package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.Configuration.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class SystemService {
    private static final Logger log = LoggerFactory.getLogger(SystemService.class);
    private void setDate(int year, int month, int day){
        try {
            Runtime.getRuntime().exec(new String[]{"timedatectl", "set-time", String.format("%d-%d-%d", year, month, day)});
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
    private void setTime(int hour, int minute) {
        try {
            Runtime.getRuntime().exec(new String[]{"timedatectl", "set-time", String.format("%d:%d", hour, minute)});
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public void applyConfig(SystemConfig a) {
        setDate(a.year(), a.month(), a.day());
        setTime(a.hour(), a.minute());
        playVideoFile(makeVideoFilePlayable(a.video()));
    }
    private void playVideoFile(File videoFile){
        try {
            Runtime.getRuntime().exec(new String[]{"vlc", videoFile.getAbsolutePath()});
        } catch (IOException e) {
            log.error("Error running vlc");
            log.error(e.toString());
        }
    }
    private File makeVideoFilePlayable(MultipartFile video) {
        File cachedVideo = new File("./assets/video.mp4");
        try {
            cachedVideo.delete();
            cachedVideo.createNewFile();
            InputStream src = video.getInputStream();
            OutputStream dst = null;
            dst = new FileOutputStream(cachedVideo);
            src.transferTo(dst);
        } catch (IOException e) {
            log.error(e.toString());
        }
        return cachedVideo;
    }
}
