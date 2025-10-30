package org.robotdolphins.pitsystem;

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

    private List<MatchRow> matchRows = new ArrayList<>();
    private boolean connected;
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

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
            matchRowsTemp.addAll(oldMatchRows);
            if (matchRowsTemp.isEmpty()) {
                throw e;
            }
        }
        matchRows = matchRowsTemp.stream().toList();
        this.connected = connected;
    }

    @GetMapping("/schedule")
    public String getSchedule(Model model) throws IOException {
        model.addAttribute("MatchRows", matchRows);
        model.addAttribute("connected", connected);
        model.addAttribute("configuration", ConfigService.configuration);
        return "schedule";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("configuration", ConfigService.configuration);
        model.addAttribute("goToSchedule", false);
        return "settings";
    }

    @PostMapping("/settings")
    public String checkSettings(@ModelAttribute Config configuration, Model model) {
        ConfigService.configuration = new Config(configuration.baseUrl(), configuration.eventCode(), configuration.token(), configuration.teamNumber(), ConfigService.configuration.formatting());
        model.addAttribute("configuration", ConfigService.configuration);
        model.addAttribute("goToSchedule", true);
        refreshSchedule();
        return "settings";
    }

    @GetMapping("/styledpage.css")
    public String getStyleSheet(Model model) {
        return "styledpage.css";
    }
}