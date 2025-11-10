package org.ball.repository;

import org.ball.entity.PlayerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerHistoryRepository extends JpaRepository<PlayerHistory, Integer> {
    List<PlayerHistory> findByPlayerId(Long playerId);

}
