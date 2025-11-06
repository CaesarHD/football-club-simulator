package org.ball;

import jakarta.annotation.PostConstruct;
import org.ball.controller.ClubController;
import org.ball.entity.*;
import org.ball.repository.ClubRepository;
import org.ball.repository.MatchRepository;
import org.ball.repository.PlayerRepository;
import org.ball.service.ClubService;
import org.ball.service.GoalService;
import org.ball.service.MatchService;
import org.ball.service.PlayerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@SpringBootApplication
public class Main {

    private final ClubService clubService;
    private final PlayerService playerService;
    private final MatchService matchService;
    private final GoalService goalService;

    public Main(ClubService clubService, PlayerService playerService, MatchService matchService, GoalService goalService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.matchService = matchService;
        this.goalService = goalService;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {


        Club liverpool = clubService.getClubByName("Liverpool");
        Club psg = clubService.getClubByName("PSG");
        Match match = new Match.Builder()
                .homeClub(liverpool)
                .awayClub(psg)
                .addGoal(12, playerService.getPlayerByClubAndName(liverpool, "Mohamed Salah"), GoalType.PENALTY)
                .addGoal(54, playerService.getPlayerByClubAndName(liverpool, "Virgil van Dijk"), GoalType.FREE_KICK)
                .date(LocalDateTime.of(2025, 7, 24, 19, 0))
                .build();

//        matchService.saveMatch(match);
    }
}