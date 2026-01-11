package org.ball.domain;

import jakarta.persistence.Entity;

public record LoginInfo(String username, String password) {
}
