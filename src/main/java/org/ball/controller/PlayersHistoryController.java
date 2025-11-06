package org.ball.controller;

import org.ball.entity.PlayersHistory;
import org.ball.service.PlayerService;
import org.ball.service.PlayersHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController

//TODO: maybe merge this with PlayerController
@RequestMapping("/players-history")
public class PlayersHistoryController {

    private final PlayersHistoryService playersHistoryService;

    public PlayersHistoryController(PlayersHistoryService playersHistoryService) {
        this.playersHistoryService = playersHistoryService;
    }

    @GetMapping("/{playerId}")
    public List<PlayersHistory> getPlayerHistory(@PathVariable Long playerId) {
        return playersHistoryService.getHistoryByPlayerId(playerId);
    }
}
