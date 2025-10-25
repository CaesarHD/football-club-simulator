package org.ball.repository;

import org.ball.entity.PlayersHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayersHistoryRepository extends JpaRepository<PlayersHistory, Integer> {
    List<PlayersHistory> findByPlayerId(Long playerId);

}
