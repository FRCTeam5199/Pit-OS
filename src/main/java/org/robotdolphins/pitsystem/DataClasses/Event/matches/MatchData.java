package org.robotdolphins.pitsystem.DataClasses.Event.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchData(Match[] matches) {

}
