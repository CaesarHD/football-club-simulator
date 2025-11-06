package org.ball.service;

import org.ball.entity.Club;
import org.ball.entity.Player;
import org.ball.repository.ClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClubService {
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
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

}
