package org.ball.repository;

import org.ball.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m FROM Match m " +
            "JOIN FETCH m.homeClub hc " +
            "JOIN FETCH m.awayClub ac " +
            "WHERE hc.name = :clubName OR ac.name = :clubName " +
            "ORDER BY m.date ASC")
    List<Match> findAllMatchesByClub(@Param("clubName") String clubName);

    @Query("SELECT m FROM Match m " +
            "WHERE FUNCTION('YEAR', m.date) = :year " +
            "ORDER BY m.date ASC")
    List<Match> findAllMatchesByYear(@Param("year") int year);

    @Query("SELECT m FROM Match m " +
            "JOIN FETCH m.homeClub hc " +
            "JOIN FETCH m.awayClub ac " +
            "WHERE FUNCTION('YEAR', m.date) = :year " +
            "AND (hc.name = :clubName OR ac.name = :clubName)" +
            "ORDER BY m.date ASC")
    List<Match> findAllMatchesByYearAndClubName(int year, String clubName);

    Match findMatchById(Long id);
}
