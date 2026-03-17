package org.robotdolphins.pitsystem.DataClasses.Event.matches.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alliance(int score, String[] team_keys) {
}
