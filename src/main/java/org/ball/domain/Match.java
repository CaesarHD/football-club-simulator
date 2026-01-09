package org.ball.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToOne
    @JoinColumn(name = "home_team_stats_id")
    private TeamMatchStats homeTeamStats;

    @OneToOne
    @JoinColumn(name = "away_team_stats_id")
    private TeamMatchStats awayTeamStats;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayerMatchStats> homeTeam;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayerMatchStats> awayTeam;

    public Match() {}

    public Match(Builder builder) {
        this.homeClub = builder.homeClub;
        this.awayClub = builder.awayClub;
        this.date = builder.date;
        this.goals = builder.goals != null ? builder.goals : new ArrayList<>();

        this.homeTeamStats = new TeamMatchStats();
        this.awayTeamStats = new TeamMatchStats();

        this.homeTeamStats.setStrategy(MatchStrategy.BALANCED);
        this.awayTeamStats.setStrategy(MatchStrategy.BALANCED);

        this.homeTeamStats.setFormation(Formation.F4_3_3);
        this.awayTeamStats.setFormation(Formation.F4_3_3);

        for (Goal goal : builder.goals) {
            goal.setMatch(this);
        }
    }

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

    public int getHomeTeamNoGoals() {
        if (goals == null || homeClub == null)
            return 0;

        return (int) goals.stream()
                .filter(g -> g != null && g.getClub() != null)
                .filter(g -> g.getClub().equals(homeClub))
                .count();
    }

    public int getAwayTeamNoGoals() {
        if (goals == null || awayClub == null)
            return 0;

        return (int) goals.stream()
                .filter(g -> g != null && g.getClub() != null)
                .filter(g -> g.getClub().equals(awayClub))
                .count();
    }


    public Long getId() { return id; }

    public Club getHomeClub() { return homeClub; }

    public Club getAwayClub() { return awayClub; }

    public void setHomeClub(Club homeClub) { this.homeClub = homeClub; }

    public void setAwayClub(Club awayClub) { this.awayClub = awayClub; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public List<Goal> getGoals() { return goals; }

    public void setGoals(List<Goal> goals) { this.goals = goals; }

    public TeamMatchStats getHomeTeamStats() { return homeTeamStats; }

    public TeamMatchStats getAwayTeamStats() { return awayTeamStats; }

    public void setHomeTeamStats(TeamMatchStats stats) { this.homeTeamStats = stats; }

    public void setAwayTeamStats(TeamMatchStats stats) { this.awayTeamStats = stats; }

    public List<PlayerMatchStats> getHomeTeam() { return homeTeam; }

    public List<PlayerMatchStats> getAwayTeam() { return awayTeam; }

    public void setHomeTeam(List<PlayerMatchStats> homeTeam) { this.homeTeam = homeTeam; }

    public void setAwayTeam(List<PlayerMatchStats> awayTeam) { this.awayTeam = awayTeam; }

    public int getSeason() { return season; }

    public void setSeason(int season) { this.season = season; }

    @Override
    public String toString() {
        return "Match{" +
                "homeClub=" + homeClub.getName() +
                ", awayClub=" + awayClub.getName() +
                ", homeTeamScore=" + getHomeTeamNoGoals() +
                ", awayTeamScore=" + getAwayTeamNoGoals() +
                ", date=" + date +
                '}';
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

        public Match build() { return new Match(this); }
    }
}
