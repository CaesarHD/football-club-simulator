package org.ball.service;

import org.ball.domain.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static org.ball.domain.UserRole.*;

@Service
public class AuthService {

    private final Map<Long, UserRole> roles;

    public AuthService() {
        this.roles = new HashMap<>();
        roles.put(1L, GUEST);
        roles.put(2L, PLAYER);
        roles.put(3L, COACH);
        roles.put(4L, MANAGER);
        roles.put(5L, ADMIN);
    }

    public UserRole getRole(Long userId) {
        return roles.get(userId);
    }

    public UserInfo login(LoginInfo loginInfo) {
        try {
            //TODO: get userId, role, all user data from db by username and password

//            if(userInfo == null) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
//            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }


        return new UserInfo(1L,"Marcel", "Bostan", getRole(1L));
    }

}
