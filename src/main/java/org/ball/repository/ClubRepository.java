package org.ball.repository;

import org.ball.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club getClubByName(String name);

    List<Club> findByIdNot(Long excludedClubId);

    Club findById(Long id);

    Club getClubById(Long clubId);
}
