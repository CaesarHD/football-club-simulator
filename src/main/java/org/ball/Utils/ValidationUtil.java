package org.ball.Utils;

import org.apache.logging.log4j.util.Strings;
import org.ball.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationUtil {

    private static final Logger log = LoggerFactory.getLogger(ValidationUtil.class);

    public static void validateDate(LocalDateTime date) {
        if (date == null) {
            String msg = "Date is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        validateSeason(date.getYear());
    }

    public static void validateGoal(Goal goal) {
        if (goal == null) {
            String msg = "Goal is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        if (goal.getMatch() == null) {
            String msg = "Match is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        if(goal.getMinute() < 0 || goal.getMinute() > 90) {
            String msg = "Minutes must be between 0 and 90.";
            log.warn(msg);
            throw new IllegalStateException(msg);
        }

        if(goal.getPlayer() == null) {
            String msg = "Player is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        if(goal.getGoalType() == null) {
            String msg = "GoalType is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

//        validateClub(goal.getClub());
    }


    public static void validateGoals(List<Goal> goals) {
        for(Goal goal: goals) {
            validateGoal(goal);
        }
    }

    public static void validatePlayer(Player player) {
        if(player == null) {
            String msg = "Player is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        validateName(player.getName());
    }

    public static void validateName(String name) {
        if(name == null || name.isEmpty()) {
            String msg = "Name is null or empty";
            log.warn("Name is null or empty");
            throw new NullPointerException(msg);
        }
    }

    public static void validateClub(Club club) {
        if (club == null) {
            throw new IllegalArgumentException("Club is null");
        }

        validateName(club.getName());

        if (club.getId() == null) {
            throw new IllegalStateException("Club must be persisted");
        }
    }

    public static void validateSeason(int season) {
        if (season < Constants.FIRST_SEASON_YEAR || season > DataUtil.getCurrentYear()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid season: " + season);
        }
    }

    public static void validateMatch(Match match) {

        if (match == null) {
            String msg =  "Match is null";
            log.warn(msg);
            throw new NullPointerException (msg);
        }

        Club homeClub = match.getHomeClub();
        Club awayClub = match.getAwayClub();

        if (match.getHomeTeamStats() == null) {
            String msg = "HomeTeamStats is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        if (match.getAwayTeamStats() == null) {
            String msg = "AwayTeamStats is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        validateClub(homeClub);
        validateClub(awayClub);
        validateDate(match.getDate());
        validateGoals(match.getGoals());

    }

    public static void validateCoach(Coach coach) {

        if(coach == null) {
            String msg = "Coach is null";
            log.warn(msg);
            throw new NullPointerException(msg);
        }

        validateName(coach.getName());

    }


}
