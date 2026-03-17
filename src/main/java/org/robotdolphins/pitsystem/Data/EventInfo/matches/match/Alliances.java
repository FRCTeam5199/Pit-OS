package org.robotdolphins.pitsystem.Data.EventInfo.matches.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Alliances(Alliance blue, Alliance red) {
}
