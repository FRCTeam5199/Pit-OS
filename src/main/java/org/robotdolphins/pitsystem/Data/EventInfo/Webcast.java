package org.robotdolphins.pitsystem.Data.EventInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Webcast(WebcastTypes type, String channel) implements Comparable<Webcast> {

    @Override
    public int compareTo(Webcast o) {
        return this.type.compareTo(o.type)*100000 + this.channel.compareTo(o.channel);
    }
}
