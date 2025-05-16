package org.robotdolphins.pitsystem;

import org.robotdolphins.pitsystem.event.matches.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private MatchServices matchServices;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/schedule")
    public String getSchedule(Model model) {
        List<Match> matches = matchServices.getMatchesForEvent();
        List<MatchRow> rows = matchServices.getMatchRowsForUI();
        model.addAttribute("matches", matches);
        model.addAttribute("rows", rows);
        return "schedule";
    }

    @GetMapping("/div")
    public String getDiv(Model model) {
        List<Match> matches = matchServices.getMatchesForEvent();
        List<MatchRow> rows = matchServices.getMatchRowsForUI();
        model.addAttribute("matches", matches);
        model.addAttribute("rows", rows);
        return "div";
    }
}