package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.matches.Match;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class MatchServices {
    private static final String BASE_URL = "https://www.thebluealliance.com/api/v3/event/";

    public List<Match> getMatchesForEvent() {
        String url = BASE_URL + "/2025caav" + "/matches";
        HttpHeaders headers = new HttpHeaders();

        try {
            BufferedReader br = new BufferedReader(new FileReader("./assets/AuthKey"));
            headers.set("X-TBA-Auth-Key", br.readLine());
            br.close();
            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<List<Match>> response = new RestTemplate().exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<Match>>() {
                    }
            );
            return response.getBody();
        } catch (IOException e) {
            System.err.println("Error reading Auth Key: " + e.getMessage());
        }
        return null;
    }

    public List<Match> filterFor5199(List<Match> allMatches) {
        List<Match> filteredMatches = new ArrayList<>(List.of());

        for (Match match : allMatches) {
            for (String team : match.getAlliances().red().team_keys()) {
                if (team.contains("5199")) filteredMatches.add(match);
            }
            for (String team : match.getAlliances().blue().team_keys()) {
                if (team.contains("5199")) filteredMatches.add(match);
            }
        }

        return filteredMatches;
    }

    public List<String> teamList(List<String> teams) {
        for (int i = 0; i < 3; i++) {
            teams.set(i, teams.get(i).substring(3));
        }
        return teams;
    }


    public List<MatchRow> getMatchRowsForUI() {
        List<Match> matchesForEvent = getMatchesForEvent();

        matchesForEvent = filterFor5199(matchesForEvent);

        List<MatchRow> matchRows = new ArrayList<>();

        for (Match match : matchesForEvent) {
            matchRows.add(MatchRow.builder()
                    .competitionLevel(match.getComp_level())
                    .number(MatchRow.dataCleanse.getCompetitionNumber(match.getComp_level(), match.getMatch_number(), match.getSet_number()))
                    .time(MatchRow.dataCleanse.getFormattedTime(match.getPredicted_time()))
                    .winner(match.getWinning_alliance())
                    .redAlliance(teamList(Arrays.asList(match.getAlliances().red().team_keys())))
                    .redScore("" + match.getAlliances().red().score())
                    .blueScore("" + match.getAlliances().blue().score())
                    .blueAlliance(teamList(Arrays.asList(match.getAlliances().blue().team_keys())))
                    .build());
        }
        Collections.sort(matchRows);
        return matchRows;
    }

}
