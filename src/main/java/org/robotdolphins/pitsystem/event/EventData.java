package org.robotdolphins.pitsystem.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EventData(String name, String key) { }
