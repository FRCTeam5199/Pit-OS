package org.robotdolphins.pitsystem;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import org.robotdolphins.pitsystem.event.Event;

@Service
public class EventInformationService {
    private static final Logger log = LoggerFactory.getLogger(EventInformationService.class);
    public static Event getEvent() {
        RestClient eventRestClient = RestClient.builder()
                .baseUrl(ConfigService.configuration.baseUrl())
                .defaultHeader("X-TBA-Auth-Key", ConfigService.configuration.token())
                .build();
        // TODO: find a good place to put "event/"
        final String URL_PATH = "event/" + ConfigService.configuration.eventCode();
        try {
            return eventRestClient.get()
                    .uri(new URI(URL_PATH))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (HttpClientErrorException e) {
            log.error("Failed to get the event list for team: {}", ConfigService.configuration.teamNumber());
            return new Event(null, null, null, null, null, null);
        }
    }
}
