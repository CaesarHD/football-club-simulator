package org.ball.service;

import org.ball.domain.*;
import org.ball.repository.CoachRepository;
import org.ball.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.validateCoach;

@Service
public class CoachService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);

    private final CoachRepository coachRepository;
    private final TransferRequestRepository transferRequestRepository;

    public CoachService(CoachRepository coachRepository, PlayerRepository playerRepository, TransferRequestRepository transferRequestRepository) {
        this.coachRepository = coachRepository;
        this.transferRequestRepository = transferRequestRepository;
    }

    public Coach saveCoach(Coach coach) {
        validateCoach(coach);

        Coach result;
        try {

            log.debug("Saving coach with id {}", coach.getId());

            result = coachRepository.save(coach);

            log.debug("Saved coach with id {}", coach.getId());
        } catch (Exception e) {
            log.error("Error while saving coach with id {}", coach.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not communicate with DB");
        }

        return result;
    }

    public List<Coach> getAllCoaches() {

        List<Coach> coaches;
        try {
            log.debug("Retrieving all coaches");
            coaches = coachRepository.findAll();
            log.debug("Retrieving all coaches successfully");
        } catch (Exception e) {
            log.error("Error while retrieving all coaches", e);
            throw new RuntimeException(e);
        }

        return coaches;
    }

    public Coach getCoachByClub(Club club) {

        Coach coach;
        try {
             coach = coachRepository.getCoachByClub(club);
            log.debug("Retrieving coach from club {}", club.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return coach;
    }

    public Coach getCoachById(Long coachId) {
        Coach coach;
        try {
            coach = coachRepository.getCoachById(coachId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return coach;
    }


//    public void sendRequestToManager(Player player, Coach coach) {
//        if(player == null) {
//            log.error("player is null");
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "player is null");
//        }
//
//        if(coach == null) {
//            log.error("coach is null");
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "coach is null");
//        }
//        TransferRequest transferRequest = new TransferRequest(player, coach.getClub());
//        transferRequestRepository.save(transferRequest);
//    }

}
