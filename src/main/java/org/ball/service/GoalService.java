package org.ball.service;

import org.ball.domain.Goal;
import org.ball.domain.Match;
import org.ball.repository.GoalRepository;
import org.ball.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.*;

@Service
public class GoalService {
    private final MatchRepository matchRepository;
    private static final Logger log = LoggerFactory.getLogger(GoalService.class);
    private final GoalRepository goalRepository;
    public GoalService(GoalRepository goalRepository, MatchRepository matchRepository) {
        this.goalRepository = goalRepository;
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
