package org.ball.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String position;
    private Integer pace;
    private Integer stamina;
    private Integer defending;
    private Integer physical;
    private Integer dribbling;
    private Integer shooting;
    private Integer passing;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDefending(Integer defending) {
        this.defending = defending;
    }

    public void setDribbling(Integer dribbling) {
        this.dribbling = dribbling;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPosition(String position) {
        this.position = position;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public int getAge() {
        return age;
    }

    public Integer getDefending() {
        return defending;
    }

    public Integer getDribbling() {
        return dribbling;
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

    public String getPosition() {
        return position;
    }

    public Integer getShooting() {
        return shooting;
    }

    public Integer getStamina() {
        return stamina;
    }

    @Override
    public String toString() {
        return "Player{id=" + id + ", name='" + name + "'}";
    }
}
