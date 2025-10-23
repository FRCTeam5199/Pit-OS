package org.robotdolphins.pitsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@EnableScheduling
@Controller
public class WebController {

    @Autowired
    private MatchServices matchServices;

    private List<MatchRow> matchRows = new ArrayList<>();
    private boolean connected;

    @Scheduled(fixedDelay = 10000)
    public void refreshSchedule(){
        List<MatchRow> matchRowsTemp = new ArrayList<>(matchRows.stream().toList());
        boolean connected = false;
        List<MatchRow> oldMatchRows = new ArrayList<>(matchRowsTemp.stream().toList());
        matchRowsTemp.clear();
        try{
            matchRowsTemp.addAll(matchServices.getMatchRowsForUI());
            connected = true;
        } catch (RestClientException e) {
            matchRowsTemp.addAll(oldMatchRows);
            System.err.println(e.getMessage());
            if (matchRowsTemp.isEmpty()){
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
        model.addAttribute("config", ConfigService.defaultConfig);
        return "schedule";
    }
    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("TeamNumber", ConfigService.teamNum);
        model.addAttribute("ApiKey", ConfigService.token);
        return "settings";
    }
    @PostMapping("/settings")
    public String checkSettings(Model model){
        return "settings";
    }
    @GetMapping("/styledpage.css")
    public String getStyleSheet(Model model){
        return "styledpage.css";
    }
}