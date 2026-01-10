package org.ball.service;

import org.ball.domain.UserRole;

public record UserInfo(long userId, String firstName, String lastName, UserRole role ) {

}
