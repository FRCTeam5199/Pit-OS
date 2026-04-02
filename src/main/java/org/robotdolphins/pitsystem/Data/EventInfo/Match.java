package org.robotdolphins.pitsystem.Data.EventInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.robotdolphins.pitsystem.Data.MatchType;
import org.robotdolphins.pitsystem.Data.EventInfo.MatchInfo.Alliances;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    int match_number;
    long predicted_time;
    long actual_time;
    Alliances alliances;
    MatchType comp_level;
    String winning_alliance;

    public int getSet_number() {
        return set_number;
    }

    public void setSet_number(int set_number) {
        this.set_number = set_number;
    }

    public String getWinning_alliance() {
        return winning_alliance;
    }

    public void setWinning_alliance(String winning_alliance) {
        this.winning_alliance = winning_alliance;
    }

    public int getMatch_number() {
        return match_number;
    }

    public void setMatch_number(int match_number) {
        this.match_number = match_number;
    }

    public MatchType getComp_level() {
        return comp_level;
    }

    public void setComp_level(MatchType comp_level) {
        this.comp_level = comp_level;
    }

    public Alliances getAlliances() {
        return alliances;
    }

    public void setAlliances(Alliances alliances) {
        this.alliances = alliances;
    }

    public long getActual_time() {
        return actual_time;
    }

    public void setActual_time(long actual_time) {
        this.actual_time = actual_time;
    }

    public long getPredicted_time() {
        return predicted_time;
    }

    public void setPredicted_time(long predicted_time) {
        this.predicted_time = predicted_time;
    }

    int set_number;
}
