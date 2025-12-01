package org.ball.service;

import org.apache.logging.log4j.util.Strings;
import org.ball.Utils.Constants;
import org.ball.Utils.DataUtil;
import org.ball.domain.*;
import org.ball.repository.GoalRepository;
import org.ball.repository.MatchRepository;
import org.ball.repository.PlayerMatchStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final GoalRepository goalRepository;
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    public MatchService(MatchRepository matchRepository,
                        PlayerMatchStatsRepository playerMatchStatsRepository,
                        GoalRepository goalRepository) {
        this.matchRepository = matchRepository;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
        this.goalRepository = goalRepository;
    }

    public void saveMatch(Match match) {

        validateMatch(match);

        try {
            log.info("Saving match {}", match);

            Match saved = matchRepository.save(match);

            saveMatchGoals(saved.getGoals());

            log.info("Match {} saved successfully", saved.getId());

        } catch (Exception e) {
            log.error("Error saving match {}", match, e);
            throw new IllegalStateException("Error saving match", e);
        }
    }


    private void saveMatchGoals(List<Goal> goals) {

        validateGoals(goals);

        log.info("Saving match goals {}", goals);

        for (Goal goal : goals) {
            try {
                goalRepository.save(goal);
            } catch (Exception e) {
                log.error("Error saving match goal {}", goal, e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Could not save goal");
            }
        }
    }

    public List<Match> getMatchesBySeasonAndClubName(int season, String clubName) {

        validateSeason(season);
        validateName(clubName);

        try {
            return matchRepository.findAllMatchesByYearAndClubName(season, clubName);
        } catch (Exception e) {
            log.error("Error fetching matches by season and club", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch matches");
        }
    }

    public Match getMatchById(Long matchId) {

        if (matchId == null || matchId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match ID invalid");
        }

        try {
            Match match = matchRepository.findMatchById(matchId);

            if (match == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
            }

            return match;

        } catch (Exception e) {
            log.error("Error fetching match {}", matchId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch match");
        }

    }

    public List<PlayerMatchStats> getAllPlayersMatchStatsByMatchIdAndClubId(Long matchId, Long clubId) {
        if (matchId == null || matchId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match ID invalid");
        }
        if (clubId == null || clubId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club ID invalid");
        }

        try {
            return playerMatchStatsRepository.findAllByMatchIdAndClubId(matchId, clubId);
        } catch (Exception e) {
            log.error("Error fetching player match stats", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch player match stats");
        }
    }
}
