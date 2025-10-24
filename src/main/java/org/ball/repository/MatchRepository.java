package org.ball.repository;

import org.ball.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(
            value = """
                    SELECT m.* 
                    FROM matches m
                    JOIN clubs c ON m.home_club_id = c.id
                    WHERE c.name = :homeClubName
                    """,
            nativeQuery = true
    )
    List<Match> findAllMatchesByClub(@Param("homeClub") String homeClub);

}
