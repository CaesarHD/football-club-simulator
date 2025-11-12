package org.ball.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stadiums")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int capacity;

    public Stadium() {}

    public Stadium(int capacity, String name) {
        this.capacity = capacity;
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

}
