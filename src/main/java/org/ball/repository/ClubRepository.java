package org.ball.repository;

import org.ball.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club getClubByName(String name);

}
