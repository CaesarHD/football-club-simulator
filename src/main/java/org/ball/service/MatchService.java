package org.ball.service;

import org.apache.logging.log4j.util.Strings;
import org.ball.Utils.Constants;
import org.ball.Utils.DataUtil;
import org.ball.entity.Club;
import org.ball.entity.Goal;
import org.ball.entity.Match;
import org.ball.repository.GoalRepository;
import org.ball.entity.PlayerMatchStats;
import org.ball.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.ball.repository.PlayerMatchStatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final GoalRepository goalRepository;
    private static final Logger log =  LoggerFactory.getLogger(MatchService.class);

    public MatchService(MatchRepository matchRepository, PlayerMatchStatsRepository playerMatchStatsRepository, GoalRepository goalRepository) {
        this.matchRepository = matchRepository;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
        this.goalRepository = goalRepository;
    }


    public List<PlayerMatchStats> getAllPlayersMatchStatsByMatchAndClub(Long matchId, Long clubId) {

        List<PlayerMatchStats> matches;
        try {
            log.info("Searching for all matches with matchId {} and clubId {}", matchId, clubId);
            matches = playerMatchStatsRepository.findAllByMatchIdAndClubId(matchId, clubId);
            log.info("Found {} matches with matchId {} and clubId {}", matches.size(), matchId, clubId);
        } catch (Exception e) {
            log.warn("Found 0 matches for matchId {} and clubId {}", matchId, clubId);
            throw new RuntimeException(e);
        }
        return matches;
    }

    public static int getCurrentSeason() {
        return DataUtil.getCurrentYear();
    }

    public static int getFirstSeason() {
        return Constants.FIRST_SEASON_YEAR;
    }

    public void saveMatch(Match match) {
        validateMatch(match);

        try {
            log.debug("Saving match {}", match);
            matchRepository.save(match);
            log.debug("Match {} saved", match);
            log.debug("Saving goals {}",  match.getGoals());
            saveMatchGoals(match.getGoals());
            log.debug("Goals {} saved",  match.getGoals());
        } catch (Exception e) {
            log.error("Error saving match {}", match, e);
            throw new IllegalStateException("Error saving match", e);
        }
    }

    private void saveMatchGoals(List<Goal> goals) {
        log.info("Saving match goals {}", goals);

        for (Goal goal : goals) {
            try {
                goalRepository.save(goal);
            } catch (Exception e) {
                log.error("Error saving match goal {}", goal, e);
                throw new RuntimeException(e);
            }
        }

        log.info("Match goals saved");
    }

    public List<Match> getMatchesBySeasonAndClubName(int season, String clubName) {
        validateSeason(season);
        validateName(clubName);

        List<Match> matches;
        try {
            log.info("Getting matches by season {}", season);
            matches = matchRepository.findAllMatchesByYearAndClubName(season, clubName);
            log.info("Found {} matches", matches.size());
        } catch (Exception e) {
            log.warn("Matches for season {} and club name {} not found", season, clubName, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Matches for season " + season + " and club name " + clubName);
        }
        return matches;
    }



    private static void validateSeason(int season) {
        if (season < getFirstSeason() || season > getCurrentSeason()) {
            log.warn("No matches found for season {}", season);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matches found for season " + season);
        }
    }

    public List<Match> getMatchesByClubName(String clubName) {
        List<Match> matches;
        try {
            log.info("Getting matches by club name {}", clubName);
            matches = matchRepository.findAllMatchesByClub(clubName);
            log.info("Found {} matches", matches.size());
        } catch (Exception e) {
            log.warn("Matches for Club {} not found", clubName, e);
            throw new RuntimeException(e);
        }
        return matches;
    }

    public List<Match> getMatchesBySeason(int year) {
        return matchRepository.findAllMatchesByYear(year);
    }


    public Match getMatchById(Long matchId) {
        Match match;
        try {
            log.info("Getting match {}", matchId);
            match = matchRepository.findMatchById(matchId);
            log.info("Match {} found", match);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return match;
    }
}
