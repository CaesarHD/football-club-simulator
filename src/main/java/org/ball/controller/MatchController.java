package org.ball.controller;

import org.ball.Utils.Constants;
import org.ball.domain.*;
import org.ball.service.GoalService;
import org.ball.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.*;

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
    public List<Goal> getMatchGoals(@PathVariable("matchId") Long matchId) {

        if (matchId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "matchId is required");
        }

        Match match = matchService.getMatchById(matchId);
        if (match == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }

        List<Goal> goals = goalService.getGoals(match);
        return goals != null ? goals : new ArrayList<>();
    }

    @GetMapping("/seasons")
    public Map<String, Integer> getSeasons() {
        return Map.of(
                "firstSeason", Constants.FIRST_SEASON_YEAR,
                "latestSeason", Year.now().getValue()
        );
    }

    @GetMapping("/details/{matchId}/{clubId}")
    public Map<String, Object> getMatchDetails(
            @PathVariable Long matchId,
            @PathVariable Long clubId) {

        Match match = matchService.getMatchById(matchId);
        if (match == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }

        List<PlayerMatchStats> players =
                matchService.getAllPlayersMatchStatsByMatchIdAndClubId(matchId, clubId);

        boolean isHome = match.getHomeClub().getId().equals(clubId);

        TeamMatchStats stats = isHome
                ? match.getHomeTeamStats()
                : match.getAwayTeamStats();

        if (stats == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "TeamMatchStats not loaded for the match");
        }

        Map<String, Object> teamStats = Map.of(
                "possession", stats.getPossession(),
                "shots", stats.getShots(),
                "passes", stats.getPasses(),
                "corners", stats.getCorners(),
                "formation", stats.getFormation(),
                "strategy", stats.getStrategy()
        );

        return Map.of(
                "teamStats", teamStats,
                "players", players,
                "matchDate", match.getDate()
        );
    }


    @PutMapping("/player-status")
    public void updatePlayerStatus(@RequestBody Map<String, Object> body) {
        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long playerId = Long.valueOf(body.get("playerId").toString());

        PlayerStatusInMatch startStatus = PlayerStatusInMatch.valueOf(body.get("startStatus").toString());
        PlayerStatusInMatch endStatus = PlayerStatusInMatch.valueOf(body.get("endStatus").toString());

        matchService.changePlayerStatus(matchId, playerId, startStatus, endStatus);
    }


}
