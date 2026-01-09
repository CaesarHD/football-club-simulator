package org.ball.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.apache.bcel.classfile.Utility;
import org.ball.Utils.ValidationUtil;
import org.ball.domain.*;
import org.ball.service.AuthService;
import org.ball.service.CoachService;
import org.ball.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.domain.UserRole.COACH;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private static final Logger log = LoggerFactory.getLogger(CoachController.class);
    private final AuthService authService;
    private final CoachService coachService;
    private final MatchService matchService;

    public CoachController(CoachService coachService, AuthService authService, MatchService matchService) {
        this.coachService = coachService;
        this.authService = authService;
        this.matchService = matchService;
    }

    @GetMapping
    public List<Coach> getAllCoaches() {
        return coachService.getAllCoaches();
    }


//    @PostMapping
//    public String changeFormation(String formation, HttpServletRequest request) {
//        String userId = request.getHeader("UserId");
//        UserRole role = authService.getRole(Integer.parseInt(userId));
//
//        service
//
//    }

    @PostMapping
    public void changeFormation(@RequestParam Formation formation, @RequestParam Long matchId, HttpServletRequest request) {
        long coachId = Long.parseLong(request.getHeader("UserId"));
        if (authService.getRole(coachId) != COACH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to perform this action");
        }
        Coach coach;
        try {
            coach = coachService.getCoachById(coachId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach with id " + coachId + " not found");
        }

        Match match;
        try {
            match = matchService.getMatchById(matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        matchService.changeFormation(match, formation, coach);
    }


//    @PostMapping
//    public void changeMatchStrategy(@RequestParam MatchStrategy matchStrategy, @RequestParam Long matchId, HttpServletRequest request) {
//        long coachId = Long.parseLong(request.getHeader("UserId"));
//        if(authService.getRole(coachId) != COACH) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to perform this action");
//        }
//        Coach coach;
//        try {
//            coach = coachService.getCoachById(coachId);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach with id " + coachId + " not found");
//        }
//
//        Match match;
//        try {
//            match = matchService.getMatchById(matchId);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
////        matchService.changeMatchation(match, formation, coach);
//    }

//    @GetMapping
//    public Club getCurrentClub(@PathVariable("coachId") Long coachId) {
//        Coach coach = coachService.getCoachById(coachId);
//        ValidationUtil.validateCoach(coach);
//        return coach.getClub();
//    }

    public Coach saveCoach(Coach coach) {
        return coachService.saveCoach(coach);
    }

    @PutMapping("/transfers{transferId}")
    public void approveTransfer(@PathVariable("transferId") Long transferId) {

    }
}
