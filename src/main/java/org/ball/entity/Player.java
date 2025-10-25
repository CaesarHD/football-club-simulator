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

    @ManyToOne
    @JoinColumn(name = "current_club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private Position position;
    private Integer pace = 0 ;
    private Integer defending = 0;
    private Integer dribbling = 0;
    private Integer shooting = 0;
    private Integer physical = 0;
    private Integer passing = 0;
    private Integer stamina = 0;
    private Integer reflexes = 0;
    private Integer diving = 0;

    public Player() {}

    public Player(String name) {
        this.name = name;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public void setDiving(Integer diving) {
        this.diving = diving;
    }

    public void setReflexes(Integer reflexes) {
        this.reflexes = reflexes;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Long getId() {
        return id;
    }

    public Club getClub() {
        return club;
    }

    public Integer getDiving() {
        return diving;
    }

    public Integer getReflexes() {
        return reflexes;
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

    public Position getPosition() {
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
