package org.ball.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "home_club_id")
    private Club homeClub;

    @OneToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

//    private List<Goal> homeGoals;
//    private List<Goal> awayGoals;

    private int homeTeamScore;
    private int awayTeamScore;

    private LocalDateTime date;

    public Game(Club homeClub, Club awayClub, List<Goal> homeGoals, List<Goal> awayGoals, LocalDateTime date) {
        this.homeClub = homeClub;
        this.awayClub = awayClub;
//        this.homeGoals = homeGoals;
//        this.awayGoals = awayGoals;
//        this.homeTeamScore = homeGoals.size();
//        this.awayTeamScore = awayGoals.size();
        this.date = date;
    }

    public Game() {

    }

    public Club getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(Club awayClub) {
        this.awayClub = awayClub;
    }

    public Club getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(Club homeClub) {
        this.homeClub = homeClub;
    }

    public Long getId() {
        return id;
    }

//    public List<Goal> getHomeGoals() {
//        return homeGoals;
//    }
//
//    public void setHomeGoals(List<Goal> homeGoals) {
//        this.homeGoals = homeGoals;
//    }
//
//    public List<Goal> getAwayGoals() {
//        return awayGoals;
//    }
//
//    public void setAwayGoals(List<Goal> awayGoals) {
//        this.awayGoals = awayGoals;
//    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
