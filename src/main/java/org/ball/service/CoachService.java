package org.ball.service;

import org.ball.entity.Coach;
import org.ball.repository.CoachRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CoachService {
    private static final Logger log = LoggerFactory.getLogger(CoachService.class);

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public Coach saveCoach(Coach coach) {
        //NOTE: example of Service method with logging and exception handling
        Coach result;

        try {
            if (coach == null) {
                log.warn("Coach is null");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coach payload is mandatory, cannot be null");
            }

            if (coach.getName() == null || coach.getName().isEmpty()) {
                //todo: implement this
            }

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
        return coachRepository.findAll();
    }
}
