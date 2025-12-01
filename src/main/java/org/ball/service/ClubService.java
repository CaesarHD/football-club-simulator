package org.ball.service;

import org.ball.domain.Club;
import org.ball.domain.Stadium;
import org.ball.repository.ClubRepository;
import org.ball.repository.StadiumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClubService {
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;


    public ClubService(ClubRepository clubRepository, StadiumRepository stadiumRepository) {
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
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
        if(name == null || name.isEmpty()) {
            log.warn("Club name is mandatory, cannot be empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club name is mandatory, cannot be empty");
        }
        Club club;
        try {
            log.info("Getting club {}", name);
            club = clubRepository.getClubByName(name);
            log.info("Returning club {}", club);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return club;
    }

    public Club saveClub(Club club, int stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new RuntimeException("Stadium not found"));
        club.setStadium(stadium);
        return clubRepository.save(club);
    }

}
