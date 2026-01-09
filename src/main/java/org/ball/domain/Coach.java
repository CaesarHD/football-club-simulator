package org.ball.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double salaryPerSeason;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public Coach() {}

    public Coach(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalaryPerSeason(double salaryPerSeason) {
        this.salaryPerSeason = salaryPerSeason;
    }

    public double getSalaryPerSeason() {
        return salaryPerSeason;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
