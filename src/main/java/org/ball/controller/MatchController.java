package org.ball.controller;

import org.ball.entity.Match;
import org.ball.repository.MatchRepository;
import org.ball.service.MatchService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{clubName}/{year}")
    public List<Match> getMatchesBySeason(@PathVariable("clubName") String clubName,
                                          @PathVariable("year") int year) {
        return matchService.getMatchesBySeasonAndClubName(year, clubName);
    }

}
