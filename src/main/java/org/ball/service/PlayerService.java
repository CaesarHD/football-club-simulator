package org.ball.service;

import org.ball.repository.ClubRepository;
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
    private final ClubRepository clubRepository;
    private final PlayerClubInterestRepository playerClubInterestRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerHistoryRepository playersHistoryRepository, ClubRepository clubRepository, PlayerClubInterestRepository playerClubInterestRepository) {
        this.playerRepository = playerRepository;
        this.playersHistoryRepository = playersHistoryRepository;
        this.clubRepository = clubRepository;
        this.playerClubInterestRepository = playerClubInterestRepository;
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
        validateName(playerName);

        log.info("Finding player by club {} and name {}", club.getName(), playerName);

        Player result = playerRepository.findPlayerByNameAndClubId(playerName, club.getId());

        if (result == null) {
            throw new NullPointerException("Player " + playerName + " not found in club " + club.getName());
        }

        log.info("Found player {}, club={}",
                result.getName(),
                result.getClub().getName());

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

    public List<Club> findAllOtherClubs(Player player) {
        if(player == null) {
            log.error("Player is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is null");
        }
        Club club = player.getClub();
        if (club == null || club.getId() == null) {
            return clubRepository.findAll();
        }
        Long myClubId = player.getClub().getId();
        return clubRepository.findByIdNot(myClubId);
    }

    public void setInterestForCertainClub(Player player, Club club) {
        if(player == null) {
            log.error("Player is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is null");
        }

        if(club == null) {
            log.error("Club is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club is null");
        }

        if(player.getClubsToJoin().contains(club)) {
            return;
        }

        player.getClubsToJoin().add(club);
    }

    public void removeInterestForCertainClub(Player player, Club club) {
        if(player == null) {
            log.error("Player is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is null");
        }

        if(club == null) {
            log.error("Club is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club is null");
        }

        if(player.getClubsToJoin().contains(club)) {
            player.getClubsToJoin().remove(club);
        }
    }
}
