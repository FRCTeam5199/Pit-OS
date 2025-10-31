package org.robotdolphins.pitsystem.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String key) {
}
