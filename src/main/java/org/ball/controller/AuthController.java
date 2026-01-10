package org.ball.controller;

import org.ball.service.AuthService;
import org.ball.service.LoginInfo;
import org.ball.service.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/api/login")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public UserInfo login(@RequestBody LoginInfo loginInfo) {
        return authService.login(loginInfo);
    }
}
