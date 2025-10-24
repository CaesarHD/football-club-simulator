package org.ball.repository;

import org.ball.entity.PlayersHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersHistoryRepository extends JpaRepository<PlayersHistory, Integer> {
}
