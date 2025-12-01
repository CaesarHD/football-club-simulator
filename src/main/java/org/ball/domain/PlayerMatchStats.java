package org.ball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "player_match_stats")
public class PlayerMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "status_start")
    @Enumerated(EnumType.STRING)
    private PlayerStatusInMatch statusAtTheStartOfTheMatch;

    @Column(name = "status_end")
    @Enumerated(EnumType.STRING)
    private PlayerStatusInMatch statusAtTheEndOfTheMatch;

    public PlayerMatchStats() {}

    public PlayerMatchStats(Match match, Club club, Player player) {
        this.match = match;
        this.club = club;
        this.player = player;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatusAtTheStartOfTheMatch(PlayerStatusInMatch status) {
        this.statusAtTheStartOfTheMatch = status;
    }

    public void setStatusAtTheEndOfTheMatch(PlayerStatusInMatch status) {
        this.statusAtTheEndOfTheMatch = status;
    }

    public Club getClub() {
        return club;
    }

    public Long getId() {
        return id;
    }

    public Match getMatch() {
        return match;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public PlayerStatusInMatch getStatusAtTheStartOfTheMatch() {
        return statusAtTheStartOfTheMatch;
    }

    public PlayerStatusInMatch getStatusAtTheEndOfTheMatch() { return statusAtTheEndOfTheMatch; }
}
