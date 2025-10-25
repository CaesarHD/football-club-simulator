package org.ball;

import jakarta.annotation.PostConstruct;
import org.ball.entity.Match;
import org.ball.entity.Player;
import org.ball.repository.ClubRepository;
import org.ball.repository.MatchRepository;
import org.ball.repository.PlayerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@SpringBootApplication
public class Main {

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final MatchRepository matchRepository;

    public Main(PlayerRepository playerRepository, ClubRepository clubRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
        this.matchRepository = matchRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {

    }
}