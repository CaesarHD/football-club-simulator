package org.ball.controller;

import org.ball.service.AuthService;
import org.ball.service.LoginInfo;
import org.ball.service.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller("/api/login")
public class AuthController {

//    private final AuthService authService;
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

//    @PostMapping
//    public UserInfo login(@RequestBody LoginInfo loginInfo) {
//        return authService.login(loginInfo);
//    }

    private final UserRepository userRepo;
    private final PlayerRepository playerRepo;

    public AuthController(UserRepository userRepo, PlayerRepository playerRepo) {
        this.userRepo = userRepo;
        this.playerRepo = playerRepo;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials"));

        if (!u.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials");
        }

        Map<String, Object> out = new HashMap<>();
        out.put("role", u.getRole());
        out.put("username", u.getUsername());

        if ("PLAYER".equalsIgnoreCase(u.getRole())) {
            Player p = playerRepo.findByUserId(u.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "user has no player"));
            out.put("playerId", p.getId());
        }

        return out;
    }
}
