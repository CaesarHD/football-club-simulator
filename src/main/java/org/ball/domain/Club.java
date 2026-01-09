package org.ball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int budget;
    private String country;

    @OneToOne(mappedBy = "club")
    @JsonIgnore
    private Coach coach;

    @OneToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private List<Player> players =  new ArrayList<>();

    @OneToOne(mappedBy = "club")
    @JsonIgnore
    private Manager manager;

    public Club() {}

    public Club(String name,  int budget, Stadium stadium) {
        this.name = name;
        this.budget = budget;
        this.stadium = stadium;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budget=" + budget +
                ", country='" + country + '\'' +
                ", coach=" + coach +
                ", stadium=" + stadium +
                ", players=" + players +
                ", manager=" + manager +
                '}';
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

}
