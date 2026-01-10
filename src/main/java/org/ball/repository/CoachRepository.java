package org.ball.repository;

import org.ball.domain.Club;
import org.ball.domain.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachRepository extends JpaRepository<Coach, Long> {


    Coach getCoachByClub(Club club);

    Coach getCoachById(Long id);

    Coach getCoachByName(String name);
}
