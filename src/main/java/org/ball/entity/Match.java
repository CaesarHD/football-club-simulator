package org.ball.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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
    private List<Goal> homeGoals;
    @Transient
    private List<Goal> awayGoals;

    @Transient
    private Integer season;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void postLoadOrPersist() {
        if (this.date == null) {
            this.season = null;
        } else {
            this.season = this.date.getYear();
        }

    }

    private void calculateGoals() {
        homeTeamScore = homeGoals.size();
        awayTeamScore = awayGoals.size();
    }

    public Match(Builder builder) {
        this.homeClub = builder.homeClub;
        this.awayClub = builder.awayClub;
        this.date = builder.date;
        this.goals = builder.goals;

        for (Goal goal : builder.goals) {
            goal.setMatch(this);
        }

        homeGoals = extractGoalsOf(homeClub);
        awayGoals = extractGoalsOf(awayClub);

        calculateGoals();
    }



    public Match() {}

    private List<Goal> extractGoalsOf(Club club) {
        List<Goal> filteredGoals = goals.stream()
                .filter(goal -> goal.getPlayer().getClub().getId().equals(club.getId()))
                .collect(Collectors.toList());

        return filteredGoals.isEmpty() ? Collections.emptyList() : filteredGoals;
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

    public List<Goal> getHomeGoals() {
        return homeGoals;
    }

    public List<Goal> getAwayGoals() {
        return awayGoals;
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
