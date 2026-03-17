package org.robotdolphins.pitsystem.Configuration;

import org.robotdolphins.pitsystem.Data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class ConfigChoices {
    @Autowired
    private ConfigService configService;

    private RestClient configInfoClient;
    public ConfigChoices(ConfigService configService) {
        RestClient.Builder builder = RestClient.builder();
        builder.baseUrl(configService.getConfiguration().baseUrl());
        builder.defaultHeader("X-TBA-Auth-Key", configService.getConfiguration().token());
        configInfoClient = builder
                .build();
    }
    public void refreshClient() {
        configInfoClient = RestClient.builder()
                .baseUrl(configService.getConfiguration().baseUrl())
                .defaultHeader("X-TBA-Auth-Key", configService.getConfiguration().token())
                .build();
    }

    public String[] getEventKeys() {
        String eventListLocation = String.format("/team/%s/events", configService.getConfiguration().teamNumber());
        Event[] eventData = Objects.requireNonNull(configInfoClient.get()
                .uri(eventListLocation)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<Event[]>() {
                }));
        String[] eventKeys = new String[eventData.length];
        for (int i = 0; i < eventData.length; i++) {
            eventKeys[i] = eventData[i].key();
        }
        return eventKeys;
    }

    public boolean isApiKeyValid(String apiKey) {
        return configInfoClient
                .get()
                .uri("status")
                .header("X-TBA-Auth-Key", apiKey)
                .retrieve()
                .toBodilessEntity()
                .getStatusCode()
                .equals(HttpStatusCode.valueOf(200));
    }
}
