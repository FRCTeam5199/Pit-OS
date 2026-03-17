package org.robotdolphins.pitsystem.Data.EventInfo.matches.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alliance(int score, String[] team_keys) {
}
