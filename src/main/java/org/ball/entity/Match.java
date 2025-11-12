package org.ball.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Goal> goals = new ArrayList<>();

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

    @JsonProperty("homeTeamNoGoals")
    public int getHomeTeamNoGoals() {
        if (homeClub == null || goals == null) return 0;
        return (int) goals.stream().filter(g -> g.getClub().equals(homeClub)).count();
    }

    @JsonProperty("awayTeamNoGoals")
    public int getAwayTeamNoGoals() {
        return (int) goals.stream().filter(g -> g.getClub().equals(awayClub)).count();
    }

    public Match(Builder builder) {
        this.homeClub = builder.homeClub;
        this.awayClub = builder.awayClub;
        this.date = builder.date;
        this.goals = builder.goals;

        for (Goal goal : builder.goals) {
            goal.setMatch(this);
        }

    }

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<PlayerMatchStats> homeTeam;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<PlayerMatchStats> awayTeam;

    public Match() {}

    public List<Goal> getGoals() {
        return goals;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Game{" +
                "homeClub=" + homeClub.getName() +
                ", awayClub=" + awayClub.getName() +
                ", homeTeamScore=" + this.getHomeTeamNoGoals() +
                ", awayTeamScore=" + this.getAwayTeamNoGoals() +
                ", date=" + date +
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

        public Builder addGoal(int minute, Player scorer, Club club, GoalType goalType) {
            Goal goal = new Goal(minute, club, scorer, goalType);
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
