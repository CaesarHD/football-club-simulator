package org.ball.service;

import org.ball.entity.Manager;
import org.ball.entity.PlayersHistory;
import org.ball.repository.ManagerRepository;
import org.ball.repository.PlayerRepository;
import org.ball.repository.PlayersHistoryRepository;

import java.util.List;

public class PlayersHistoryService {

    private final PlayersHistoryRepository playersHistoryRepository;


    public PlayersHistoryService(PlayersHistoryRepository playersHistoryRepository) {
        this.playersHistoryRepository = playersHistoryRepository;
    }

    public PlayersHistory savePlayersHistory(PlayersHistory playersHistory) {
        return playersHistoryRepository.save(playersHistory);
    }

    public List<PlayersHistory> getAllPlayersHistory() {
        return playersHistoryRepository.findAll();
    }
}
