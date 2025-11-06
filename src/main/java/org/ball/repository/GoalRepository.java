package org.ball.repository;

import org.ball.entity.Goal;
import org.ball.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findGoalsByMatch(Match match);
    List<Goal> findByMatch(Match match);
}
