package org.ball.repository;

import org.ball.domain.PlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats, Integer> {

    List<PlayerMatchStats> findAllByMatchIdAndClubId(Long matchId, Long clubId);

}
