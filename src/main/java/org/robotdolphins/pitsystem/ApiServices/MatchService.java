package org.robotdolphins.pitsystem.ApiServices;

import org.robotdolphins.pitsystem.Configuration.ConfigService;
import org.robotdolphins.pitsystem.Data.MatchRow;
import org.robotdolphins.pitsystem.Data.EventInfo.matches.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class MatchService {
    @Autowired
    private ConfigService configService;
    public List<Match> getMatches() {
        RestClient matchesRestClient = RestClient.builder()
                .baseUrl(configService.getConfiguration().baseUrl())
                .defaultHeader("X-TBA-Auth-Key", configService.getConfiguration().token())
                .build();
        final String MATCH_LOCATION = String.format(configService.getConfiguration().matchLocation(),configService.getConfiguration().eventCode());
        try {
            return matchesRestClient.get()
                    .uri(new URI(MATCH_LOCATION))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Match> filterFor5199(List<Match> allMatches) {
        List<Match> filteredMatches = new ArrayList<>();

        for (Match match : allMatches) {
            for (String team : match.getAlliances().red().team_keys()) {
                if (team.contains(String.valueOf(configService.getConfiguration().teamNumber()))) filteredMatches.add(match);
            }
            for (String team : match.getAlliances().blue().team_keys()) {
                if (team.contains(String.valueOf(configService.getConfiguration().teamNumber()))) filteredMatches.add(match);
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


    public List<MatchRow> getMatchRowsForUI() throws RestClientException {
        List<Match> matchesForEvent = getMatches();

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
                    .build(configService));
        }
        Collections.sort(matchRows);
        return matchRows;
    }
}
