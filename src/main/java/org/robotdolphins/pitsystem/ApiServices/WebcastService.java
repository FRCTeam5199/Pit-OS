package org.robotdolphins.pitsystem.ApiServices;

import org.robotdolphins.pitsystem.Data.Event;
import org.robotdolphins.pitsystem.Data.EventInfo.Webcast;
import org.robotdolphins.pitsystem.Data.EventInfo.WebcastTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
public class WebcastService {
    @Autowired
    private EventInformationService eventInformationService;
    private static final Logger log = LoggerFactory.getLogger(WebcastService.class);
    public List<Webcast> getWebcasts(){
        Event currentEvent = eventInformationService.getEvent();
        List<Webcast> webcasts = currentEvent.webcasts();
        Collections.sort(webcasts);
        return webcasts;
    }
    public Webcast getMainWebcast() {
        try{
            getWebcasts().getFirst();
        } catch (NoSuchElementException e){
            return new Webcast(WebcastTypes.invalid, "none");
        }
        log.info("Main webcast is {}", getWebcasts().getFirst().type());
        log.info("Main webcast's channel is {}", getWebcasts().getFirst().channel());
        return getWebcasts().getFirst();
    }
    public List<String> getChannels(ArrayList<Webcast> webcasts) {
        ArrayList<String> channels = new ArrayList<>();
        for (Webcast cast : webcasts) {
            channels.add(cast.channel());
        }
        return channels;
    }
}
