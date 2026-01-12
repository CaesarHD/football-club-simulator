package org.ball.controller;

import org.ball.domain.*;
import org.ball.service.AuthService;
import org.ball.service.ClubService;
import org.ball.service.CoachService;
import org.ball.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

import static org.ball.domain.UserRole.COACH;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private static final Logger log = LoggerFactory.getLogger(CoachController.class);

    private final AuthService authService;
    private final MatchService matchService;
    private final CoachService coachService;
    private final ClubService clubService;

    public CoachController(AuthService authService, MatchService matchService, CoachService coachService, ClubService clubService) {
        this.authService = authService;
        this.matchService = matchService;
        this.coachService = coachService;
        this.clubService = clubService;
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
        log.info("Received request to update formation of coach with id: {}", userId);
        ensureCoach(userId);

        Long matchId = Long.valueOf(body.get("matchId").toString());
        Long clubId = Long.valueOf(body.get("clubId").toString());
        Formation formation = Formation.valueOf(body.get("formation").toString());

        verifyCoachPermissions(userId, clubId, matchId);

        matchService.updateFormation(matchId, clubId, formation);
    }

    private void verifyCoachPermissions(Long userId, Long clubId, Long matchId) {
        Coach coach = coachService.getCoachByUserId(userId);
        Club clubToModify = clubService.getClubById(clubId);
        Match match = matchService.getMatchById(matchId);

        if (match.getDate().isBefore(LocalDateTime.now())) {
            log.error("You're trying to update a past match.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're trying to update a past match.");
        }

        if(!match.getHomeClub().equals(clubToModify) && !match.getAwayClub().equals(clubToModify)) {
            log.error("The match not containing your club.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The match not containing your club.");
        }

        if (!coach.getClub().equals(clubToModify)) {
            log.error("The club is not yours.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The club is not yours.");
        }
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

        verifyCoachPermissions(userId, clubId, matchId);
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
        Long clubId = Long.valueOf(body.get("clubId").toString());
        PlayerStatusInMatch startStatus = PlayerStatusInMatch.valueOf(body.get("startStatus").toString());
        PlayerStatusInMatch endStatus = PlayerStatusInMatch.valueOf(body.get("endStatus").toString());

        verifyCoachPermissions(userId, clubId, matchId);

        matchService.updatePlayerStatus(matchId, playerId, startStatus, endStatus);
    }

    @PutMapping("/approve")
    @ResponseStatus(HttpStatus.OK)
    public void approveTransfer(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("transferId")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transferId is required");
        }

        Long transferId;
        try {
            transferId = Long.valueOf(body.get("transferId").toString());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transferId");
        }

        coachService.approveTransfer(transferId);
    }

    @DeleteMapping("/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectTransfer(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("transferId")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transferId is required");
        }

        Long transferId;
        try {
            transferId = Long.valueOf(body.get("transferId").toString());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transferId");
        }

        coachService.rejectTransfer(transferId);
    }
}
