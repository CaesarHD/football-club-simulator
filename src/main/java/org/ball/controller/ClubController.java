package org.ball.controller;

import org.ball.domain.Club;
import org.ball.domain.Match;
import org.ball.domain.Transfer;
import org.ball.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    public static Logger log = LoggerFactory.getLogger(ClubController.class);
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("")
    public List<Club> getClubs() {
        return clubService.getAllClubs();
    }

    @PostMapping("/transfers{playerId}/{clubId}")
    public Transfer createTransfer(@PathVariable("playerId") Long playerId, @PathVariable("clubId") Long clubId) {
        return clubService.createTransfer(playerId, clubId);
    }

    @GetMapping("/transfers")
    public List<Transfer> getAllTransfers(@RequestParam("clubId") Long clubId) {
        return clubService.getAllTransfers(clubId);
    }


    @GetMapping("/next_match")
    public Match getNextMatch(@RequestParam("clubId") Long clubId) {
        return clubService.getNextMatch(clubId);
    }
}
