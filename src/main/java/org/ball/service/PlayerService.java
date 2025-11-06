package org.ball.service;

import org.ball.entity.Club;
import org.ball.entity.Player;
import org.ball.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    public static final Logger log = LoggerFactory.getLogger(PlayerService.class);
    private final PlayerRepository playerRepository;


    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAllWithClub();
    }


    public Player getPlayerByClubAndName(Club club, String playerName) {

        Player player;
        try {
            log.info("Finding player by club {} and name {}", club.getName(), playerName);
            player = playerRepository.findPlayerByNameAndClubId(playerName, club.getId());
            log.info("Found player by club {} and name {}", club.getName(), playerName);
        } catch (Exception e) {
            throw new NullPointerException("Could not find player by club and name " + playerName);
        }
        return player;
    }
}
