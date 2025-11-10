package org.ball.controller;

import org.ball.Utils.Constants;
import org.ball.entity.Goal;
import org.ball.entity.Match;
import org.ball.entity.PlayerMatchStats;
import org.ball.service.GoalService;
import org.ball.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final GoalService goalService;

    public MatchController(MatchService matchService, GoalService goalService) {
        this.matchService = matchService;
        this.goalService = goalService;
    }

    @GetMapping("/club/{clubName}/{year}")
    public List<Match> getMatchesBySeason(@PathVariable("clubName") String clubName,
                                          @PathVariable("year") int year) {
        return matchService.getMatchesBySeasonAndClubName(year, clubName);
    }

    @GetMapping("/stats/{matchId}/{clubId}")
    public List<PlayerMatchStats> getPlayerMatchStatsForClub(
            @PathVariable Long matchId,
            @PathVariable Long clubId) {

        return matchService.getAllPlayersMatchStatsByMatchIdAndClubId(matchId, clubId);
    }



    @GetMapping("/goals/{matchId}")
    public List<Goal> getGoals(@PathVariable("matchId") Long matchId) {
        System.out.println("Fetching goals for matchId = " + matchId);

        if (matchId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "matchId is required");
        }

        Match match = matchService.getMatchById(matchId);
        if (match == null) {
            System.out.println("Match not found for ID: " + matchId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }

        System.out.println("Match found: " + match.getId() + " – fetching goals...");
        List<Goal> goals = goalService.getGoals(match);

        if (goals == null) {
            System.out.println("goalService returned null – returning empty list");
            return new ArrayList<>();
        }

        return goals;
    }

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
