package org.ball.domain;

import jakarta.persistence.*;

@Entity
@Table(name ="managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public Manager() {}

    public Manager(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Manager(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Club getClub() {
        return club;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", age=" + age +
                ", club=" + club.getName() +
                ", name='" + name + '\'' +
                '}';
    }
}
