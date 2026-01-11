package org.ball.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ball.domain.*;
import org.ball.service.AuthService;
import org.ball.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.ball.domain.UserRole.COACH;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private static final Logger log = LoggerFactory.getLogger(CoachController.class);

    private final AuthService authService;
    private final MatchService matchService;

    public CoachController(AuthService authService, MatchService matchService) {
        this.authService = authService;
        this.matchService = matchService;
    }

    private void ensureCoach(Long userId) {
        if (authService.getRole(userId) != COACH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You must be a coach to perform this action");
        }
    }

    @PutMapping("/match/formation")
    public void updateFormation(
            @RequestBody Map<String, Object> body,
            @RequestHeader("XUserId") Long userId
    ) {
        log.debug("Received request to update formation of coach with id: {}", userId);
        ensureCoach(userId);

        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long clubId = Long.valueOf(body.get("clubId").toString());
        Formation formation = Formation.valueOf(body.get("formation").toString());

        matchService.updateFormation(matchId, clubId, formation);
    }

    @PutMapping("/match/strategy")
    public void updateStrategy(
            @RequestBody Map<String, Object> body,
            @RequestHeader("XUserId") Long userId
    ) {
        ensureCoach(userId);

        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long clubId = Long.valueOf(body.get("clubId").toString());
        MatchStrategy strategy = MatchStrategy.valueOf(body.get("strategy").toString());

        matchService.updateStrategy(matchId, clubId, strategy);
    }

    @PutMapping("/match/player-status")
    public void updatePlayerStatus(
            @RequestBody Map<String, Object> body,
            @RequestHeader("XUserId") Long userId
    ) {
        ensureCoach(userId);

        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long playerId = Long.valueOf(body.get("playerId").toString());
        PlayerStatusInMatch startStatus = PlayerStatusInMatch.valueOf(body.get("startStatus").toString());
        PlayerStatusInMatch endStatus= PlayerStatusInMatch.valueOf(body.get("endStatus").toString());

        matchService.updatePlayerStatus(matchId, playerId, startStatus, endStatus);
    }
}
