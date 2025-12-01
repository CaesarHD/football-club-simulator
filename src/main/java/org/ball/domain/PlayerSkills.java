package org.ball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "player_skills")
public class PlayerSkills {

    @Id
    private Long id;   // not generated!

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Integer pace = 0;
    private Integer defending = 0;
    private Integer dribbling = 0;
    private Integer shooting = 0;
    private Integer physical = 0;
    private Integer passing = 0;
    private Integer stamina = 0;
    private Integer reflexes = 0;
    private Integer diving = 0;

    public PlayerSkills() {}

    public PlayerSkills(Player player, Position position) {
        this.player = player;
    }

    public Integer getDefending() {
        return defending;
    }

    public Integer getDiving() {
        return diving;
    }

    public Integer getDribbling() {
        return dribbling;
    }

    public Long getId() {
        return id;
    }

    public Integer getPace() {
        return pace;
    }

    public Integer getPassing() {
        return passing;
    }

    public Integer getPhysical() {
        return physical;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public Integer getReflexes() {
        return reflexes;
    }

    public Integer getShooting() {
        return shooting;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setDefending(Integer defending) {
        this.defending = defending;
    }

    public void setDiving(Integer diving) {
        this.diving = diving;
    }

    public void setDribbling(Integer dribbling) {
        this.dribbling = dribbling;
    }

    public void setPace(Integer pace) {
        this.pace = pace;
    }

    public void setPassing(Integer passing) {
        this.passing = passing;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setReflexes(Integer reflexes) {
        this.reflexes = reflexes;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }
}
