package org.robotdolphins.pitsystem;

import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchRow implements Comparable<MatchRow> {
    private final MatchType competitionLevel;
    private final int number;
    private final String time;
    private final String winner;
    private final List<StyledText> redAlliance;
    private final String redScore;
    private final String blueScore;
    private final List<StyledText> blueAlliance;

    public MatchRow(MatchType competitionLevel, int number, String time, String winner, List<String> redAlliance, String redScore, String blueScore, List<String> blueAlliance) {
        this.competitionLevel = competitionLevel;
        this.number = number;
        this.time = time;
        this.winner = winner;
        this.redAlliance = formatAlliance(redAlliance.stream()
                .map((redTeam) -> new StyledText(redTeam, "normal", "#000000"))
                .collect(Collectors.toList()), "red");
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.blueAlliance = formatAlliance(blueAlliance.stream()
                .map((bluTeam) -> new StyledText(bluTeam, "normal", "#000000"))
                .collect(Collectors.toList()), "blue");
    }

    public List<StyledText> formatAlliance(List<StyledText> list, String alliance) {
        ArrayList<StyledText> formattedAlliance = new ArrayList<>();

        if (alliance.equals("red")) {
            for (StyledText text : list) {
                text.setColor(winner.equals("red") ? "#ff0000" : "#aa2222");
                text.setFontWeight(text.getText().equals(String.valueOf(ConfigService.configuration.teamNumber())) ? "bold" : "normal");
                formattedAlliance.add(text);
            }
        }
        if (alliance.equals("blue")) {
            for (StyledText text : list) {
                text.setColor(winner.equals("blue") ? "#0000ff" : "#2222aa");
                text.setFontWeight(text.getText().equals(String.valueOf(ConfigService.configuration.teamNumber())) ? "bold" : "normal");
                formattedAlliance.add(text);
            }
        }
        return formattedAlliance;
    }

    public int getNumber() {
        return number;
    }

    public MatchType getCompetitionLevel() {
        return competitionLevel;
    }

    public String getTime() {
        return time;
    }

    public String getWinner() {
        return winner;
    }

    public List<StyledText> getRedAlliance() {
        return redAlliance;
    }

    public String getRedScore() {
        return redScore;
    }

    public String getBlueScore() {
        return blueScore;
    }

    public List<StyledText> getBlueAlliance() {
        return blueAlliance;
    }

    public String getMatchName() {
        return competitionLevel + ": " + number;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "MatchRow{" +
                "number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", winner='" + winner + '\'' +
                ", redAlliance=" + redAlliance +
                ", redScore='" + redScore + '\'' +
                ", blueScore='" + blueScore + '\'' +
                ", blueAlliance=" + blueAlliance +
                '}';
    }

    @Override
    public int compareTo(@NonNull MatchRow other) {
        if (this.competitionLevel.equals(MatchType.QUALIFICATION)) {
            if (other.competitionLevel.equals(MatchType.QUALIFICATION))
                return this.number - other.number;
            return -1;
        }
        if (this.competitionLevel.equals(MatchType.FINALS)) {
            if (other.competitionLevel.equals(MatchType.FINALS))
                return this.number - other.number;
            return 1;
        }

        if (other.competitionLevel.equals(MatchType.QUALIFICATION))
            return 1;
        if (other.competitionLevel.equals(MatchType.FINALS))
            return -1;

        return this.number - other.number;
    }

    public static class Builder {
        private MatchType competitionLevel;
        private int number;
        private String time;
        private String winner;
        private String redScore;
        private String blueScore;
        private List<String> redAlliance;
        private List<String> blueAlliance;

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public Builder winner(String winner) {
            this.winner = winner;
            return this;
        }

        public Builder redScore(String redScore) {
            this.redScore = redScore;
            return this;
        }

        public Builder blueScore(String blueScore) {
            this.blueScore = blueScore;
            return this;
        }

        public Builder redAlliance(List<String> redAlliance) {
            this.redAlliance = redAlliance;
            return this;
        }

        public Builder blueAlliance(List<String> blueAlliance) {
            this.blueAlliance = blueAlliance;
            return this;
        }

        public MatchRow build() {
            return new MatchRow(competitionLevel, number, time, winner, redAlliance, redScore, blueScore, blueAlliance);
        }

        public Builder competitionLevel(MatchType compLevel) {
            this.competitionLevel = compLevel;
            return this;
        }
    }

    public static class dataCleanse {

        public static int getCompetitionNumber(MatchType compLevel, int matchNumber, int setNumber) {
            if (compLevel.equals(MatchType.SEMIFINALS))
                return setNumber;

            return matchNumber;
        }

        public static String getFormattedTime(Long epochTime) {
            if (epochTime == null)
                return "TBD";
            return Instant.ofEpochSecond(epochTime)
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("hh:mm a"));
        }
    }
}
