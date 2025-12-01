package org.ball;

import jakarta.annotation.PostConstruct;
import org.ball.domain.*;
import org.ball.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {

    private final ClubService clubService;
    private final PlayerService playerService;
    private final MatchService matchService;
    private final GoalService goalService;
    private final CoachService coachService;

    public Main(ClubService clubService, PlayerService playerService, MatchService matchService, GoalService goalService, CoachService coachService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.matchService = matchService;
        this.goalService = goalService;
        this.coachService = coachService;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {

//
        Club liverpool = clubService.getClubByName("Liverpool");
        Club barcelona = clubService.getClubByName("Barcelona");

        Player leva;
        Player salah;
        try {
            salah = playerService.getPlayerByNameAndClub(liverpool, "Salah");
            leva = playerService.getPlayerByNameAndClub(barcelona, "Lewandowski");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Match match = new Match.Builder()
//                .homeClub(liverpool)
//                .awayClub(barcelona)
////                .addGoal(12, salah, liverpool, GoalType.PENALTY)
////                .addGoal(54, leva, barcelona, GoalType.FREE_KICK)
//                .date(LocalDateTime.of(2026, 2, 24, 19, 0))
//                .build();
//
//        matchService.saveMatch(match);
//
//        Club homeClub = match.getHomeClub();
//        Coach coach = homeClub.getCoach();

//        matchService.changeFormation(match, Formation.F4_4_2, coach);
    }
}