package org.ball.repository;

import org.ball.domain.Goal;
import org.ball.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findGoalsByMatch(Match match);
    List<Goal> findByMatch(Match match);
}
