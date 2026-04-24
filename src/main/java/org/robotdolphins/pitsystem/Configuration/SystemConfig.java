package org.robotdolphins.pitsystem.Configuration;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

public record SystemConfig(int year, int month, int day, int hour, int minute, MultipartFile video) {
    public SystemConfig(LocalDateTime time) {
        this(time.getYear(),time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute(), null);
    }
}
