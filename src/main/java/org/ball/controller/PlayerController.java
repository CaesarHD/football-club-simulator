package org.ball.controller;

import org.ball.entity.Player;
import org.ball.entity.PlayersHistory;
import org.ball.service.PlayerService;
import org.ball.service.PlayersHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayersHistoryService playersHistoryService;

    public PlayerController(PlayerService playerService, PlayersHistoryService playersHistoryService) {
        this.playerService = playerService;
        this.playersHistoryService = playersHistoryService;
    }

    @GetMapping("")
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/history/{playerId}")
    public List<PlayersHistory> getPlayerHistory(@PathVariable Long playerId) {
        return playersHistoryService.getHistoryByPlayerId(playerId);
    }

}
