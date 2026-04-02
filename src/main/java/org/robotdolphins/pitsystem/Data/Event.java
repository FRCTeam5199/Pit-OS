package org.robotdolphins.pitsystem.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.robotdolphins.pitsystem.Data.EventInfo.Webcast;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String short_name, String key, List<Webcast> webcasts, Integer year, Integer week) implements Comparable<Event> {
    public String getShortName() {
        String eventName = short_name;
        if (year != null && year != LocalDate.now().getYear()) {
            eventName = year + " " + eventName;
        }
        return eventName;
    }
    public String getLongName() {
        return year + " " + name;
    }

    @Override
    public int compareTo(Event o) {
        if (year != null && o.year != null && !year.equals(o.year)) return year.compareTo(o.year);
        if (week != null && o.week != null) return week.compareTo(o.week);
        return 0;
    }
}
