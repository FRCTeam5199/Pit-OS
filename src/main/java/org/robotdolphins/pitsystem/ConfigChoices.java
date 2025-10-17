package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.EventData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class ConfigChoices {
    private RestClient configInfoClient = RestClient.builder()
            .baseUrl(ConfigService.BASE_URL)
            .defaultHeader("X-TBA-Auth-Key", ConfigService.token)
            .build();
    public void refreshClient() {
        configInfoClient = RestClient.builder()
                .baseUrl(ConfigService.BASE_URL)
                .defaultHeader("X-TBA-Auth-Key", ConfigService.token)
                .build();
    }
    public String[] getEventKeys() {
        String eventListLocation = String.format("/team/%s/events", ConfigService.teamNum);
        EventData[] eventData = Objects.requireNonNull(configInfoClient.get()
                .uri(eventListLocation)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<EventData[]>() {}));
        String[] eventKeys = new String[eventData.length];
        for (int i = 0; i < eventData.length; i++) {
            eventKeys[i] = eventData[i].key();
        }
        return eventKeys;
    }
    public boolean isApiKeyValid(String apiKey){
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
