package org.robotdolphins.pitsystem.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String key, ArrayList<Webcast> webcasts) {
}
