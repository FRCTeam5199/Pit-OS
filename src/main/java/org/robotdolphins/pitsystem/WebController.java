package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.Webcast;
import org.robotdolphins.pitsystem.event.WebcastTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Controller
public class WebController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private WebcastService webcastService;
    @Autowired
    private ConfigService configService;

    private List<MatchRow> matchRows = new ArrayList<>();
    private boolean connected;
    private static final Logger log = LoggerFactory.getLogger(WebController.class);
    private Webcast webcast = new Webcast(WebcastTypes.direct_link,"");

    @Scheduled(fixedDelay = 10000)
    public void refreshSchedule() {
        log.info("Refreshing schedule.");
        List<MatchRow> matchRowsTemp = new ArrayList<>(matchRows.stream().toList());
        boolean connected = false;
        List<MatchRow> oldMatchRows = new ArrayList<>(matchRowsTemp.stream().toList());
        matchRowsTemp.clear();
        try {
            matchRowsTemp.addAll(matchService.getMatchRowsForUI());
            connected = true;
        } catch (RestClientException e) {
            log.error("Failed to connect to TBA to get match schedules.");
            matchRowsTemp.addAll(oldMatchRows);
            if (matchRowsTemp.isEmpty()) {
                throw e;
            }
        }
        matchRows = matchRowsTemp.stream().toList();
        this.connected = connected;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 100)
    public void refreshWebcastSource() {
        Webcast tempwebcast = webcast;
        try {
            webcast = webcastService.getMainWebcast();
            this.connected = true;
        } catch (RestClientException e) {
            log.error("Failed to connect to TBA to refresh Webcast source.");
            webcast = tempwebcast;
            this.connected = false;
        }
    }

    @GetMapping("/schedule")
    public String getSchedule(Model model) throws IOException {
        log.info("Rendering Schedule");
        model.addAttribute("MatchRows", matchRows);
        model.addAttribute("connected", connected);
        model.addAttribute("configuration", configService.getConfiguration());
        //TODO: We only support youtube and twitch for now. Additional HTML code needs to be added to support all stream types.
        model.addAttribute("webcast", webcast);
        return "schedule";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("configuration", configService.getConfiguration());
        model.addAttribute("goToSchedule", false);
        return "settings";
    }

    @PostMapping("/settings")
    public String checkSettings(@ModelAttribute Config configuration, Model model) {
        configService.setConfiguration(new Config(configuration.baseUrl(), configService.getConfiguration().matchLocation(), configuration.eventCode(), configuration.token(), configuration.teamNumber(), configService.getConfiguration().formatting()));
        model.addAttribute("configuration", configService.getConfiguration());
        model.addAttribute("goToSchedule", true);
        refreshSchedule();
        return "settings";
    }

    @GetMapping("/styledpage.css")
    public String getStyleSheet(Model model) {
        return "styledpage.css";
    }
}