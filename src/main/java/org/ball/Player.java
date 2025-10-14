package org.ball;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{id=" + id + ", name='" + name + "'}";
    }
}
