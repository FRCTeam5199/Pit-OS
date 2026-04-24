package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.Configuration.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        runBinaryAsUser("vlc", new String[]{videoFile.getAbsolutePath(), "-R"});
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
    private void runBinaryAsUser(String binary, String[] args) {
        if(System.getProperty("os.name").startsWith("Windows")) {
            log.error("Running windows binaries isn't supported, tried to run {}", binary);
            return;
        }
        try {
            ArrayList<String> command = new ArrayList<String>(List.of(new String[]{"systemd-run", "--uid=rdos", "--unit=vlc.service", "-E", "DISPLAY=:0"}));
            command.add(binary);
            command.addAll(List.of(args));
            Runtime.getRuntime().exec(command.toArray(new String[0]));
        } catch (IOException f) {
            log.error("Failed to run {}", binary);
            log.error(f.toString());
        }
    }
    public void runCaptivePortal(){
        runBinaryAsUser("chromium", new String[]{"http://httpforever.com"});
    }
}
