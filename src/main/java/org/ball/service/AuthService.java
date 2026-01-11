package org.ball.service;

import org.ball.domain.LoginInfo;
import org.ball.domain.UserInfo;
import org.ball.domain.UserRole;
import org.ball.repository.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    private final Map<Long, UserRole> rolesCache = new HashMap<>();

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public UserRole getRole(Long userId) {
        return rolesCache.computeIfAbsent(userId, k -> {
            UserInfo userInfo = authRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            return userInfo.getRole();
        });
    }

    public UserInfo login(LoginInfo loginInfo) {
        UserInfo userInfo;
        try {
            if (loginInfo == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error: Invalid Login Info");
            }

            userInfo = authRepository.findByUsernameAndPassword(loginInfo.username(), loginInfo.password());

            if (userInfo == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found in DB");
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        return userInfo;
    }

}