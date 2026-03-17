package org.robotdolphins.pitsystem.Data.EventInfo.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchData(Match[] matches) {

}
