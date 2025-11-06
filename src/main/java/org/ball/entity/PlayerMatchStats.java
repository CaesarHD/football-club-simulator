package org.ball.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "match_stats")
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

    @Enumerated(EnumType.STRING)
    private PlayerStatusInMatch status;

    public PlayerMatchStats() {}

    public PlayerMatchStats(Match match, Club club, Player player) {
        this.match = match;
        this.club = club;
        this.player = player;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(PlayerStatusInMatch status) {
        this.status = status;
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

    public PlayerStatusInMatch getStatus() {
        return status;
    }
}
