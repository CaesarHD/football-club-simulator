package org.ball.repository;

import org.ball.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {


    List<Player> findByClubId(Long currentClubId);

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.club")
    List<Player> findAllWithClub();
}
