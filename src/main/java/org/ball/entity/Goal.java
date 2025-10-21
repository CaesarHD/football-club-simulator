package org.ball.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int minute;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
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
}
