package org.ball.repository;


import org.ball.domain.LoginInfo;
import org.ball.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsernameAndPassword(String username, String password);
}
