package org.ball.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_match", foreignKey = @ForeignKey(name = "fk_goal_match"))
    @JsonBackReference
    private Match match;

    @Column(name = "minute")
    private int minute;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type")
    private GoalType goalType;


    public Goal(int minute, Player player, GoalType goalType) {
        this.minute = minute;
        this.player = player;
        this.goalType = goalType;
    }

    public Goal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", minute=" + minute +
                ", player=" + player +
                ", goalType=" + goalType +
                '}';
    }
}
