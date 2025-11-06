package org.ball.service;

import org.ball.Utils.Constants;
import org.ball.Utils.DataUtil;
import org.ball.entity.Club;
import org.ball.entity.Goal;
import org.ball.entity.GoalType;
import org.ball.entity.Match;
import org.ball.repository.GoalRepository;
import org.ball.repository.MatchRepository;
import org.ball.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final GoalRepository goalRepository;
    private static final Logger log =  LoggerFactory.getLogger(MatchService.class);

    public MatchService(MatchRepository matchRepository, GoalRepository goalRepository) {
        this.matchRepository = matchRepository;
        this.goalRepository = goalRepository;
    }

    public static void validateMatch(Match match) {
        if (match == null) {
            log.warn("Match is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match payload is mandatory, cannot be null");
        }

        Club homeClub = match.getHomeClub();
        Club awayClub = match.getAwayClub();
        if (match.getHomeClub() == null) {
            log.warn("Home Club is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Home club is mandatory, cannot be null");
        }

        if (match.getAwayClub() == null) {
            log.warn("Away Club is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Away club is mandatory, cannot be null");
        }
        validateClubName(homeClub.getName());
        validateClubName(awayClub.getName());
        validateDate(match);
    }

    public static void validateDate(Match match) {
        if (match.getDate() == null) {
            log.warn("Date is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is mandatory, cannot be null");
        }
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
            log.info("Saving match {}", match);
            matchRepository.save(match);
            log.info("Match {} saved", match);
            log.info("Saving goals {}",  match.getGoals());
            saveMatchGoals(match.getGoals());
            log.info("Goals {} saved",  match.getGoals());
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
        validateClubName(clubName);

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

    private static void validateClubName(String clubName) {
        if (clubName == null || clubName.isEmpty()) {
            log.warn("Club name is null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club name can't be null or empty");
        }
    }

    private static void validateSeason(int season) {
        if (season < getFirstSeason() || season > getCurrentSeason()) {
            log.warn("No matches found for season {}", season);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matches found for season " + season);
        }
    }

    public List<Match> getMatchesByClubName(String clubName) {
        return matchRepository.findAllMatchesByClub(clubName);
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
