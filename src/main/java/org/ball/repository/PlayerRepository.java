package org.ball.repository;

import org.ball.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByClubId(Long currentClubId);

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.club LEFT JOIN FETCH p.skills")
    List<Player> findAllWithClub();

    Player findPlayerByName(String name);

    Player findPlayerByNameAndClubId(String name, Long clubId);

    Player findPlayersById(Long id);
}

