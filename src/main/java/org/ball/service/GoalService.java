package org.ball.service;

import org.ball.entity.Goal;
import org.ball.entity.Match;
import org.ball.repository.GoalRepository;
import org.ball.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.validateMatch;

@Service
public class GoalService {
    private final MatchRepository matchRepository;
    private static final Logger log = LoggerFactory.getLogger(GoalService.class);
    private final GoalRepository goalRepository;
    public GoalService(GoalRepository goalRepository1, MatchRepository matchRepository) {
        this.goalRepository = goalRepository1;
        this.matchRepository = matchRepository;
    }

    public List<Goal> getGoals(Match match) {
        validateMatch(match);
        List<Goal> goals;
        try {
            log.info("Finding goals for match {}", match);
            goals = goalRepository.findByMatch(match);
            log.info("Found {} goals for match {}", goals.size(), match);
        } catch (Exception e) {
            log.error("Error finding goals from match {}", match, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find goals from match.");
        }
        return goals;
    }

    public static void validateGoal(Goal goal) {

        log.info("Verifying goal {}", goal);
        if(goal == null) {
            log.error("Goal cannot be null");
            throw new NullPointerException("Goal cannot be null");
        }

        //TODO: validatePlayer
//        validatePlayer(goal.getPlayer);
        if(goal.getPlayer() == null) {
            log.error("Player cannot be null");
            throw new NullPointerException("Player cannot be null");
        }

        if(goal.getGoalType() == null) {
            log.error("Goal type cannot be null");
            throw new NullPointerException("Goal type cannot be null");
        }

        if(goal.getMinute() < 0 || goal.getMinute() > 90) {
            log.error("Invalid minute");
            throw new IllegalStateException("Invalid minute");
        }
    }

    public void saveGoal(Goal goal) {
        validateGoal(goal);
        log.info("Saving goal {}", goal);
        try {
            log.info("Saving goal {}", goal);
            goalRepository.save(goal);
            log.info("Saved goal {}", goal);
        } catch (Exception e) {
            log.error("Error saving goal {}", goal, e);
            throw new IllegalStateException("Error saving goal", e);
        }
    }



}
