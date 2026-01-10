package org.ball.service;

import org.ball.domain.*;
import org.ball.repository.ClubRepository;
import org.ball.repository.PlayerRepository;
import org.ball.repository.StadiumRepository;
import org.ball.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.validateClub;
import static org.ball.Utils.ValidationUtil.validateName;

@Service
public class ClubService {
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final PlayerRepository playerRepository;
    private final TransferRepository transferRepository;


    public ClubService(ClubRepository clubRepository, StadiumRepository stadiumRepository, PlayerRepository playerRepository, TransferRepository transferRepository) {
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
        this.playerRepository = playerRepository;
        this.transferRepository = transferRepository;
    }

    public List<Club> getAllClubs() {
        List<Club> clubs;
        try {
            log.info("Getting all clubs");
            clubs = clubRepository.findAll();
            log.info("Returning all clubs");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch clubs from the database.");
        }
        return clubs;
    }

    public Club getClubByName(String name) {
        validateName(name);
        Club club;
        try {
            log.info("Getting club {}", name);
            club = clubRepository.getClubByName(name);
            club.setPlayers(playerRepository.findByClubId(club.getId()));
            log.info("Returning club {}", club);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return club;
    }

    public Club saveClub(Club club) {
        validateClub(club);
        Club saved;
        try {
            log.info("Saving club {}", club);
            saved = clubRepository.save(club);
            log.info("Returning saved club {}", saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return saved;
    }

    public List<Player> getInterestedPlayersForClub(Club club) {
        if (club == null || club.getId() == null) {
            log.error("coach must be assigned to a club in order to use this feature");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "coach without a club");
        }

        return null;
    }

    public Transfer createTransfer(Long clubId, Long playerId) {
        Club club;
        Player player;
        try {
            club = clubRepository.findById(clubId);
            player = playerRepository.findPlayerById(playerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch club or player from the database.");
        }

        if(player.getClub().equals(club)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Player is already in this club");
        }

        Transfer transfer = new Transfer(club, player);
        try {
            transferRepository.save(transfer);
        } catch (Exception e) {
                log.error("Failed to save transfer", e);
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to save transfer"
                );
            }

        return transfer;
    }



}
