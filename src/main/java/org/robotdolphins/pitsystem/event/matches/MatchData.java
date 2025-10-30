package org.robotdolphins.pitsystem.event.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchData(Match[] matches) {

}
