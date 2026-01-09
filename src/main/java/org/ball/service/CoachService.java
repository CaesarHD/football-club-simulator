package org.ball.service;

import org.ball.domain.Club;
import org.ball.domain.Coach;
import org.ball.repository.CoachRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.validateCoach;
import static org.ball.Utils.ValidationUtil.validateName;

@Service
public class CoachService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
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
}
