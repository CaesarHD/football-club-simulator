package org.ball.service;

import org.ball.Utils.ValidationUtil;
import org.ball.domain.Club;
import org.ball.domain.Player;
import org.ball.domain.PlayerHistory;
import org.ball.repository.PlayerRepository;
import org.ball.repository.PlayerHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.ball.Utils.ValidationUtil.*;

@Service
public class PlayerService {

    public static final Logger log = LoggerFactory.getLogger(PlayerService.class);
    private final PlayerRepository playerRepository;
    private final PlayerHistoryRepository playersHistoryRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerHistoryRepository playersHistoryRepository) {
        this.playerRepository = playerRepository;
        this.playersHistoryRepository = playersHistoryRepository;
    }

    public Player savePlayer(Player player) {
        Player result;

        validatePlayer(player);

        try {

            log.debug("Saving player {}...", player.getName());
            result = playerRepository.save(player);
            log.debug("Saved player {}", player.getName());
        }
        catch (Exception e)  {
            log.error("Error while saving player with id {}", player.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not communicate with the db ");
        }

        return result;
    }

    public List<Player> getAllPlayers() {
        List<Player> result;
        try {
            log.debug("Getting all players ...");

            result =  playerRepository.findAllWithClub();

            log.debug("Fetched all players.");
        } catch (Exception e) {
            log.error("Error while fetching all players ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not communicate with the db ");
        }
        return result;
    }

    public Player getPlayerByNameAndClub(Club club, String playerName) {

        validateClub(club);

        Player result;
        try {
            validateName(playerName);
            log.info("Finding player by club {} and name {}", club.getName(), playerName);
            result = playerRepository.findPlayerByNameAndClubId(playerName, club.getId());
            log.info("Found player by club {} and name {}", club.getName(), playerName);
        } catch (Exception e) {
            log.error("Error while  fetching player by name {} and club with id {}", playerName, club.getId() , e);
            throw new NullPointerException("Could not find player by club and name " + playerName);
        }
        return result;
    }

    public List<PlayerHistory> getHistoryByPlayerId(Long playerId) {
        List<PlayerHistory> result;
        try {
            if(playerId == null) {
                log.warn("Player id is null");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player id is mandatory, cannot be null");
            }
            if(playerId <= 0) {
                log.warn("Player id is negative");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player id must be an integer greater than zero");
            }
            log.debug("Getting history by player id {} ...", playerId);

            result = playersHistoryRepository.findByPlayerId(playerId);

            log.debug("Fetched history by player id {} ", playerId);
        }
        catch (Exception e) {
            log.error("Error while fetching history by player id {}", playerId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not communicate with the db ");

        }
        return result;
    }
}
