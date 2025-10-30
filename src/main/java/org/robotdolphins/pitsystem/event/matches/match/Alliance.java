package org.robotdolphins.pitsystem.event.matches.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alliance(int score, String[] team_keys) {
}
