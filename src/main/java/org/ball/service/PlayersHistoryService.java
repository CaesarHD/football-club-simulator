package org.ball.service;

import org.ball.entity.Manager;
import org.ball.entity.PlayersHistory;
import org.ball.repository.ManagerRepository;
import org.ball.repository.PlayerRepository;
import org.ball.repository.PlayersHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayersHistoryService {


    private final PlayersHistoryRepository playersHistoryRepository;

    public PlayersHistoryService(PlayersHistoryRepository playersHistoryRepository) {
        this.playersHistoryRepository = playersHistoryRepository;
    }

    public List<PlayersHistory> getHistoryByPlayerId(Long playerId) {
        return playersHistoryRepository.findByPlayerId(playerId);
    }
}
