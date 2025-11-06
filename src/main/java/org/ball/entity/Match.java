package org.ball.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.ball.Utils.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "home_club_id")
    private Club homeClub;

    @OneToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Goal> goals;

    @Column(name = "home_team_score")
    private int homeTeamScore;
    @Column(name = "away_team_score")
    private int awayTeamScore;

    private LocalDateTime date;

    @Transient
    private Integer season;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateSeason() {
        if (this.date == null) {
            this.season = null;
            return;
        }
        this.season = this.date.getYear();
    }



    private void calculateGoals() {
        homeTeamScore = getHomeGoals().size();
        awayTeamScore = getAwayGoals().size();
    }

    public Match(Builder builder) {
        this.homeClub = builder.homeClub;
        this.awayClub = builder.awayClub;
        this.date = builder.date;
        this.goals = builder.goals;

        for (Goal goal : builder.goals) {
            goal.setMatch(this);
        }

        calculateGoals();
    }

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<PlayerMatchStats> homeTeam;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<PlayerMatchStats> awayTeam;

    public Match() {}
    private List<Goal> getHomeGoals() {
        return goals.stream()
                .filter(goal -> goal.getPlayer().getClub().getId().equals(homeClub.getId()))
                .collect(Collectors.toList());
    }

    private List<Goal> getAwayGoals() {
        return goals.stream()
                .filter(goal -> goal.getPlayer().getClub().getId().equals(awayClub.getId()))
                .collect(Collectors.toList());
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
    public List<Goal> getGoals() {
        return goals;
    }

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

    @Override
    public String toString() {
        return "Game{" +
                "homeClub=" + homeClub +
                ", awayClub=" + awayClub +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                ", date=" + date +
                ", goals=" + goals +
                '}';
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setAwayTeam(List<PlayerMatchStats> awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setHomeTeam(List<PlayerMatchStats> homeTeam) {
        this.homeTeam = homeTeam;
    }

    public List<PlayerMatchStats> getAwayTeam() {
        return awayTeam;
    }

    public List<PlayerMatchStats> getHomeTeam() {
        return homeTeam;
    }



    public static class Builder {
        private Club homeClub;
        private Club awayClub;
        private List<Goal> goals = new ArrayList<>();
        private LocalDateTime date;

        public Builder homeClub(Club homeClub) {
            this.homeClub = homeClub;
            return this;
        }

        public Builder awayClub(Club awayClub) {
            this.awayClub = awayClub;
            return this;
        }

        public Builder addGoal(int minute, Player scorer, GoalType goalType) {
            Goal goal = new Goal(minute, scorer, goalType);
            goals.add(goal);
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Match build() {
            return new Match(this);
        }
    }

}
