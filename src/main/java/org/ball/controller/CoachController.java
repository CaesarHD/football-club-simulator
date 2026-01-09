package org.ball.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.ball.domain.*;
import org.ball.service.AuthService;
import org.ball.service.CoachService;
import org.ball.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
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

    @PutMapping()
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

    @PutMapping("/match/formation")
    public void updateFormation(@RequestBody Map<String, Object> body) {
        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long clubId = Long.valueOf(body.get("clubId").toString());
        String formationStr = body.get("formation").toString();

        Formation formation = Formation.valueOf(formationStr); // convert string to enum
        matchService.updateFormation(matchId, clubId, formation);
    }

    @PutMapping("/match/strategy")
    public void updateStrategy(@RequestBody Map<String, Object> body) {
        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long clubId = Long.valueOf(body.get("clubId").toString());
        String strategyStr = body.get("strategy").toString();

        MatchStrategy strategy = MatchStrategy.valueOf(strategyStr); // convert string to enum
        matchService.updateStrategy(matchId, clubId, strategy);
    }


    public Coach saveCoach(Coach coach) {
        return coachService.saveCoach(coach);
    }

    @PutMapping("/transfers/approve{transferId}")
    public void approveTransfer(@PathVariable("transferId") Long transferId) {
        coachService.approveTransfer(transferId);
    }

    @PutMapping("/transfers/reject{transferId}")
    public void rejectTransfer(@PathVariable("transferId") Long transferId) {
        coachService.rejectTransfer(transferId);
    }

}
