package org.robotdolphins.pitsystem.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String key, List<Webcast> webcasts) {
}
