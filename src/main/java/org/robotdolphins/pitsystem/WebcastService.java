package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.Event;
import org.robotdolphins.pitsystem.event.Webcast;
import org.robotdolphins.pitsystem.event.WebcastTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WebcastService {
    private static final Logger log = LoggerFactory.getLogger(WebcastService.class);
    public static List<Webcast> getWebcasts(){
        Event currentEvent = EventInformationService.getEvent();
        List<Webcast> webcasts = currentEvent.webcasts();
        Collections.sort(webcasts);
        return webcasts;
    }
    public static Webcast getMainWebcast() {
        log.info("Main webcast is {}", getWebcasts().getFirst().type());
        log.info("Main webcast's channel is {}", getWebcasts().getFirst().channel());
        return getWebcasts().getFirst();
    }
    public static List<String> getChannels(ArrayList<Webcast> webcasts) {
        ArrayList<String> channels = new ArrayList<>();
        for (Webcast cast : webcasts) {
            channels.add(cast.channel());
        }
        return channels;
    }
}
