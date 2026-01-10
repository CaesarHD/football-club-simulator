package org.ball.repository;

import org.ball.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByName(String name);
}
