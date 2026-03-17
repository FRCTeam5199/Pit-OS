package org.robotdolphins.pitsystem.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.robotdolphins.pitsystem.Data.EventInfo.Webcast;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(String name, String key, ArrayList<Webcast> webcasts) {
}
