package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.matches.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class WebController {

    @Autowired
    private MatchServices matchServices;

    final List<MatchRow> matchRows = new ArrayList<>();
    @GetMapping("/schedule")
    public String getSchedule(Model model) throws IOException {
        boolean connected = false;
        List<MatchRow> oldMatchRows = new ArrayList<>(matchRows.stream().toList());
        matchRows.clear();
        try{
            matchRows.addAll(matchServices.getMatchRowsForUI());
            connected = true;
        } catch (RestClientException e) {
            matchRows.addAll(oldMatchRows);
            System.err.println(e.getMessage());
            if (matchRows.isEmpty()){
                throw e;
            }
        }
        model.addAttribute("MatchRows", matchRows);
        model.addAttribute("connected", connected);
        return "schedule";
    }
}