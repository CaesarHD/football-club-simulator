package org.ball.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players_history")
public class PlayersHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
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
}
