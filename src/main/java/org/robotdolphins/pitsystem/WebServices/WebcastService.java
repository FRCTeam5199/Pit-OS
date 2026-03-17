package org.robotdolphins.pitsystem.WebServices;

import org.robotdolphins.pitsystem.DataClasses.Event.Event;
import org.robotdolphins.pitsystem.DataClasses.Event.Webcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class WebcastService {
    @Autowired
    private EventInformationService eventInformationService;
    private static final Logger log = LoggerFactory.getLogger(WebcastService.class);
    public ArrayList<Webcast> getWebcasts(){
        Event currentEvent = eventInformationService.getEvent();
        ArrayList<Webcast> webcasts = currentEvent.webcasts();
        Collections.sort(webcasts);
        return webcasts;
    }
    public Webcast getMainWebcast() {
        log.info("Main webcast is {}", getWebcasts().getFirst().type());
        log.info("Main webcast's channel is {}", getWebcasts().getFirst().channel());
        return getWebcasts().getFirst();
    }
    public ArrayList<String> getChannels(ArrayList<Webcast> webcasts) {
        ArrayList<String> channels = new ArrayList<>();
        for (Webcast cast : webcasts) {
            channels.add(cast.channel());
        }
        return channels;
    }
}
