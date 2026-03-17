package org.robotdolphins.pitsystem.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String short_name, String key, List<Webcast> webcasts, Integer year) {
    public String getShortName() {
        String eventName = short_name;
        if (year != LocalDate.now().getYear()) {
            eventName = year + " " + eventName;
        }
        return eventName;
    }
    public String getLongName() {
        return year + " " + name;
    }
}
