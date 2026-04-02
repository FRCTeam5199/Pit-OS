package org.robotdolphins.pitsystem.Data.EventInfo.MatchInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alliance(int score, String[] team_keys) {
}
