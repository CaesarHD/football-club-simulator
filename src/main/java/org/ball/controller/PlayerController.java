package org.ball.controller;

import org.ball.domain.Player;
import org.ball.domain.PlayerHistory;
import org.ball.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("")
    public List<Player> getPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/history/{playerId}")
    public List<PlayerHistory> getPlayerHistory(@PathVariable Long playerId) {
        return playerService.getHistoryByPlayerId(playerId);
    }

    @GetMapping("/by-name/{name}")
    public Player getPlayerByName(@PathVariable String name) {
        Player p = playerService.getPlayerByName(name);
        return p;
    }


}
