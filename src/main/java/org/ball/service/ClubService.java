package org.ball.service;

import org.ball.domain.*;
import org.ball.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    private final MatchRepository matchRepository;


    public ClubService(ClubRepository clubRepository, StadiumRepository stadiumRepository, PlayerRepository playerRepository, TransferRepository transferRepository, MatchRepository matchRepository) {
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
        this.playerRepository = playerRepository;
        this.transferRepository = transferRepository;
        this.matchRepository = matchRepository;
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

    public List<Transfer> getBuyingTransfers(Long clubId) {
        Club club = getClubById(clubId);
        return transferRepository.findAllByNewClub(club);
    }

    // UPDATED: Now filters out DONE transfers
    public List<Transfer> getSellingTransfers(Long clubId) {
        Club club = getClubById(clubId);
        // Fetch players in my club, but ignore the transfer if it is already DONE
        // This prevents the "Buyer" from seeing it in their selling list after the player moves in.
        return transferRepository.findByPlayer_ClubAndTransferStatusNot(club, TransferStatus.DONE);
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

    public Club getClubById(Long clubId) {
        Club club;
        try {
            club = clubRepository.findById(clubId);
        } catch (Exception e) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch club from the database.");
        }
        return club;
    }

    public Match getNextMatch(Long clubId) {
        Club club = getClubById(clubId);
        List<Match> nextMatchByClub;
        try {
             nextMatchByClub = matchRepository.findNextMatchByClub(club.getId(), LocalDateTime.now());
        } catch (Exception e) {
            throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch matches from the database.");
        }

        nextMatchByClub.forEach(m -> log.info("Match: {}, Date: {}", m, m.getDate()));

        Match nextMatch;
        try {
            log.info("Getting next match for club {}", club);
            nextMatch = nextMatchByClub.get(0);
        } catch (Exception e) {
            log.error("Failed to fetch the next match from the database", e);
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "There is no more matches for this club.");
        }

        log.info("Returning next match for club {}", nextMatch);

        return nextMatch;
    }

    public List<Transfer> getAllTransfers(Long clubId) {
        List<Transfer> transfers;
        try {
            Club club = clubRepository.getClubById(clubId);
            transfers = transferRepository.getAllTransfersByNewClub(club);
        } catch (Exception e) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch transfers from the database.");
        }
        return transfers;
    }

    public List<Transfer> getTransfersByPlayerId(Long playerId) {
        try {
            return transferRepository.findByPlayerId(playerId);
        } catch (Exception e) {
            log.error("Failed to fetch player transfers", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching player transfers");
        }

    }

    // In ClubService.java

    public void expandStadium(Long clubId, int seatsToAdd) {
        Club club = getClubById(clubId);

        // Determine cost (Budget is in Millions)
        int cost = 0;
        int capacityIncrease = 0; // Capacity is in Thousands (k)

        switch (seatsToAdd) {
            case 1000:
                cost = 1;        // 1 Mil
                capacityIncrease = 1; // 1k
                break;
            case 5000:
                cost = 5;        // 5 Mil
                capacityIncrease = 5; // 5k
                break;
            case 10000:
                cost = 12;       // 12 Mil
                capacityIncrease = 10; // 10k
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid expansion package");
        }

        if (club.getBudget() < cost) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds! You need " + cost + " mil.");
        }

        // Deduct Budget
        club.setBudget(club.getBudget() - cost);

        // Increase Capacity
        Stadium stadium = club.getStadium();
        if (stadium != null) {
            stadium.setCapacity(stadium.getCapacity() + capacityIncrease);
            stadiumRepository.save(stadium);
        }

        // Save Club
        clubRepository.save(club);

        log.info("Stadium expanded by {} seats. Cost: {} mil. New Budget: {}", seatsToAdd, cost, club.getBudget());
    }


}
