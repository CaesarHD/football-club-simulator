package org.ball.controller;

import org.ball.Utils.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SeasonController {
    @GetMapping("/seasons")
    public Map<String, Integer> getSeasons() {
        int firstSeason = Constants.FIRST_SEASON_YEAR;
        int latestSeason = Year.now().getValue();
        return Map.of(
                "firstSeason", firstSeason,
                "latestSeason", latestSeason
        );
    }
}
