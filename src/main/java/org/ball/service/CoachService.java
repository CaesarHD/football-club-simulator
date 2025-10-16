package org.ball.service;

import org.ball.entity.Coach;
import org.ball.entity.Manager;
import org.ball.repository.CoachRepository;
import org.ball.repository.ManagerRepository;

import java.util.List;

public class CoachService {

    private final CoachRepository coachRepository;


    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public Coach saveManager(Coach coach) {
        return coachRepository.save(coach);
    }

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }
}
