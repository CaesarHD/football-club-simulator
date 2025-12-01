package org.ball.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "team_match_stats")
public class TeamMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer possession = 0;
    private Integer shots = 0;
    private Integer passes = 0;
    private Integer corners = 0;

    @Enumerated(EnumType.STRING)
    private MatchStrategy strategy;

    @Enumerated(EnumType.STRING)
    private Formation formation;

    public TeamMatchStats() {
    }

    public Integer getCorners() {
        return corners;
    }

    public Formation getFormation() {
        return formation;
    }

    public Long getId() {
        return id;
    }

    public Integer getPasses() {
        return passes;
    }

    public Integer getPossession() {
        return possession;
    }

    public Integer getShots() {
        return shots;
    }

    public MatchStrategy getStrategy() {
        return strategy;
    }

    public void setCorners(Integer corners) {
        this.corners = corners;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void setPasses(Integer passes) {
        this.passes = passes;
    }

    public void setPossession(Integer possession) {
        this.possession = possession;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public void setStrategy(MatchStrategy strategy) {
        this.strategy = strategy;
    }
}
