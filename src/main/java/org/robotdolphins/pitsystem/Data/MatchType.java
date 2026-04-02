package org.robotdolphins.pitsystem.Data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MatchType {
    QUALIFICATION("qm"), SEMIFINALS("sf"), FINALS("f");
    final String name;

    MatchType(String formattedName) {
        this.name = formattedName;
    }

    public String getFormattedName() {
        return switch (name) {
            case "f" -> "F";
            case "sf" -> "M";
            case "qm" -> "Q";
            default -> "Unknown";
        };
    }

    @JsonValue
    public String getInternalName() {
        return name;
    }

    @Override
    public String toString() {
        return getFormattedName();
    }
}
