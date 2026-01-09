package org.ball.repository;

import org.ball.domain.Match;
import org.ball.domain.Player;
import org.ball.domain.PlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats, Long> {
    Optional<PlayerMatchStats> findByPlayerAndMatch(Player player, Match match);
    List<PlayerMatchStats> findAllByMatchIdAndClubId(Long matchId, Long clubId);
}
