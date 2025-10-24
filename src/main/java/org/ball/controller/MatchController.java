package org.ball.controller;

import org.ball.entity.Match;
import org.ball.repository.MatchRepository;
import org.ball.service.MatchService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("")
    public List<Match> getMatchesByClub(@RequestParam("clubName") String clubName) {
        return matchService.getMatchesByClubName(clubName);
    }

}
