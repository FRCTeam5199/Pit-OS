package org.robotdolphins.pitsystem.WebServices;

import java.net.URI;
import java.net.URISyntaxException;

import org.robotdolphins.pitsystem.Configuration.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import org.robotdolphins.pitsystem.DataClasses.Event.Event;

@Service
public class EventInformationService {
    @Autowired
    private ConfigService configService;

    public Event getEvent(){
        RestClient eventRestClient = RestClient.builder()
                .baseUrl(configService.getConfiguration().baseUrl())
                .defaultHeader("X-TBA-Auth-Key", configService.getConfiguration().token())
                .build();
        // TODO: find a good place to put "event/"
        final String URL_PATH = "event/" + configService.getConfiguration().eventCode();
        try {
            return eventRestClient.get()
                    .uri(new URI(URL_PATH))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
