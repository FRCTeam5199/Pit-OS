package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.Event;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class TeamInformationService {
    private static final String eventLocation = "/team/%s/events";
    public static List<Event> getTeamEvents() {
        RestClient teamRestClient = RestClient.builder()
                .baseUrl(ConfigService.configuration.baseUrl())
                .defaultHeader("X-TBA-Auth-Key", ConfigService.configuration.token())
                .build();
        try {
            return teamRestClient.get()
                    .uri(new URI(String.format(eventLocation,ConfigService.configuration.teamNumber())))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
