package org.ball;

import org.ball.service.ClubService;
import org.ball.service.GoalService;
import org.ball.service.MatchService;
import org.ball.service.PlayerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

//    @PostConstruct
//    public void init() {
//
//
//        Club liverpool = clubService.getClubByName("Liverpool");
//        Club barcelona = clubService.getClubByName("Barcelona");
//
//        Match match = new Match.Builder()
//                .homeClub(liverpool)
//                .awayClub(barcelona)
//                .addHomeClubGoal(12, playerService.getPlayerByNameAndClub(liverpool, "Salah"), liverpool, GoalType.PENALTY)
//                .addAwayClubGoal(54, playerService.getPlayerByNameAndClub(liverpool, "Lewandowski"), barcelona, GoalType.FREE_KICK)
//                .date(LocalDateTime.of(2025, 7, 24, 19, 0))
//                .build();
//
//        //matchService.saveMatch(match);
//    }
}