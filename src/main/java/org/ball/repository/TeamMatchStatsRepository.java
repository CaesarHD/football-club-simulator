package org.ball.repository;

import org.ball.domain.Match;
import org.ball.domain.TeamMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMatchStatsRepository extends JpaRepository<TeamMatchStats, Long> {
}
