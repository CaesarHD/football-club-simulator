package org.ball.controller;

import org.ball.domain.LoginInfo;
import org.ball.domain.Player;
import org.ball.domain.UserInfo;
import org.ball.domain.UserRole;
import org.ball.service.AuthService;
import org.ball.service.CoachService;
import org.ball.service.ManagerService;
import org.ball.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final PlayerService playerService;
    private final CoachService coachService;
    private final ManagerService managerService;

    public AuthController(AuthService authService, PlayerService playerService, CoachService coachService, ManagerService managerService) {
        this.authService = authService;
        this.playerService = playerService;
        this.coachService = coachService;
        this.managerService = managerService;
    }

    @PostMapping("/login")
    public UserInfo login(@RequestBody LoginInfo loginInfo) {
        if (loginInfo == null ||
                loginInfo.username() == null || loginInfo.username().isBlank() ||
                loginInfo.password() == null || loginInfo.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }

        UserInfo userInfo = authService.login(new LoginInfo(loginInfo.username(), loginInfo.password()));

        if (userInfo == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return userInfo;
    }

    @PostMapping("/player_profile")
    public Player getPlayerInfo(@RequestBody UserInfo userInfo) {
        Long userId = userInfo.getId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        Player player = null;
        try {
            player = playerService.getPlayerByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        return player;
    }
}
