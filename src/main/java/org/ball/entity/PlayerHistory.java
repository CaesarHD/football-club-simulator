package org.ball.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players_history")
public class PlayerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "no_matches")
    private int noMatches = 0;

    @Column(name = "no_goals")
    private int noGoals = 0;

    @Column(name = "no_assists")
    private int noAssists = 0;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "left_date")
    private LocalDate leftDate;

    public Long getId() {
        return id;
    }

    public Club getClub() {
        return club;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public LocalDate getLeftDate() {
        return leftDate;
    }

    public int getNoAssists() {
        return noAssists;
    }

    public int getNoGoals() {
        return noGoals;
    }

    public int getNoMatches() {
        return noMatches;
    }

    public Player getPlayer() {
        return player;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public void setLeftDate(LocalDate leftDate) {
        this.leftDate = leftDate;
    }

    public void setNoAssists(int noAssists) {
        this.noAssists = noAssists;
    }

    public void setNoGoals(int noGoals) {
        this.noGoals = noGoals;
    }

    public void setNoMatches(int noMatches) {
        this.noMatches = noMatches;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
