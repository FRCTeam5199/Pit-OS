package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TeamInformationService {
    private final Logger log = LoggerFactory.getLogger(TeamInformationService.class);
    private final String eventLocation = "team/%s/events";
    public List<Event> getTeamEvents() {
        RestClient teamRestClient = RestClient.builder()
                .baseUrl(ConfigService.configuration.baseUrl())
                .defaultHeader("X-TBA-Auth-Key", ConfigService.configuration.token())
                .build();
        try {
            List<Event> events = teamRestClient.get()
                    .uri(new URI(String.format(eventLocation, "frc" + String.valueOf(ConfigService.configuration.teamNumber()))))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            if (events == null) {
                throw new NullPointerException("Rest client returned null. This is likely an issue in spring.");
            }

            Collections.sort(events);
            return events.reversed();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (HttpClientErrorException e) {
            log.error("Failed to get the event list for team: {}", ConfigService.configuration.teamNumber());
            ArrayList<Event> defaultList = new ArrayList<>();
            defaultList.add(new Event(null, null, null, null, null, null));
            return defaultList;
        }
    }
}
