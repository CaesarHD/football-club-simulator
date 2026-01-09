package org.ball.domain;

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

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerSkills skills;

    private double salary;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
        this.skills = new PlayerSkills();
        this.skills.setPlayer(this);
    }

    public Player(String name, int age, double salary, Club club, PlayerSkills skills) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.club = club;
        skills.setPlayer(this);
        this.skills = skills;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Long getId() {
        return id;
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

    public PlayerSkills getSkills() {
        return skills;
    }

    public void setSkills(PlayerSkills skills) {
        this.skills = skills;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salaryPerSeason) {
        this.salary = salaryPerSeason;
    }

//    @Override
//    public String toString() {
//        return "Player{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", club=" + club +
//                ", skills=" + skills +
//                ", salary=" + salary +
//                '}';
//    }
}
