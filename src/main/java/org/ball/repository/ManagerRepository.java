package org.ball.repository;

import org.ball.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByName(String name);
    Optional<Manager> findByUserId(Long id);
}
