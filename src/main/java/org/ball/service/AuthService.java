package org.ball.service;

import org.ball.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.ball.domain.UserRole.*;

@Service
public class AuthService {

    private final Map<Long, UserRole> roles;

    public AuthService() {
        this.roles = new HashMap<>();
        roles.put(1L, GUESS);
        roles.put(2L, PLAYER);
        roles.put(3L, COACH);
        roles.put(4L, MANAGER);
    }

    public UserRole getRole(Long userId) {
        return roles.get(userId);
    }



}
