package org.ball.service;

import jakarta.persistence.EntityNotFoundException;
import org.ball.domain.*;
import org.ball.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ball.Utils.ValidationUtil.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final GoalRepository goalRepository;
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;

    public MatchService(MatchRepository matchRepository, TeamMatchStatsRepository teamMatchStatsRepository,
                        PlayerMatchStatsRepository playerMatchStatsRepository,
                        GoalRepository goalRepository, PlayerRepository playerRepository, ClubRepository clubRepository) {
        this.matchRepository = matchRepository;
        this.teamMatchStatsRepository = teamMatchStatsRepository;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
        this.goalRepository = goalRepository;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }

    public void saveMatch(Match match) {

//        validateMatch(match);

        try {

            TeamMatchStats homeTeamMatchStats = match.getHomeTeamStats();
            TeamMatchStats awayTeamMatchStats = match.getAwayTeamStats();

            log.info("Saving match stats for match" + match);
            teamMatchStatsRepository.save(homeTeamMatchStats);
            teamMatchStatsRepository.save(awayTeamMatchStats);
            log.info("Match stats saved");

            log.info("Saving match {}", match);

            Match saved = matchRepository.save(match);

            log.info("Saved match {}", saved);

            log.info("Saving goals {}", match.getGoals());

            saveMatchGoals(match.getGoals());

            log.info("Match {} saved successfully", saved.getId());

        } catch (Exception e) {
            log.error("Error saving match {}", match, e);
            throw new IllegalStateException("Error saving match", e);
        }
    }


    private void saveMatchGoals(List<Goal> goals) {

        validateGoals(goals);

        log.info("Saving match goals {}", goals);

        for (Goal goal : goals) {
            try {
                goalRepository.save(goal);
            } catch (Exception e) {
                log.error("Error saving match goal {}", goal, e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Could not save goal");
            }
        }
    }

    public List<Match> getMatchesBySeasonAndClubName(int season, String clubName) {

        validateSeason(season);
        validateName(clubName);

        try {
            return matchRepository.findAllMatchesByYearAndClubName(season, clubName);
        } catch (Exception e) {
            log.error("Error fetching matches by season and club", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch matches");
        }
    }

    public Match getMatchById(Long matchId) {

        if (matchId == null || matchId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match ID invalid");
        }

        try {
            Match match = matchRepository.findMatchById(matchId);

            if (match == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
            }

            return match;

        } catch (Exception e) {
            log.error("Error fetching match {}", matchId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch match");
        }

    }

    public List<PlayerMatchStats> getAllPlayersMatchStatsByMatchIdAndClubId(Long matchId, Long clubId) {
        if (matchId == null || matchId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match ID invalid");
        }
        if (clubId == null || clubId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Club ID invalid");
        }

        try {
            return playerMatchStatsRepository.findAllByMatchIdAndClubId(matchId, clubId);
        } catch (Exception e) {
            log.error("Error fetching player match stats", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not fetch player match stats");
        }
    }

    public void changeFormation(Match match, Formation formation, Coach coach) {
        validateCoach(coach);
        validateMatch(match);

        Club coachClub = coach.getClub();
        TeamMatchStats matchStats;
        if(coachClub == match.getHomeClub()) {
            matchStats = match.getHomeTeamStats();
        } else {
            matchStats = match.getAwayTeamStats();
        }

        matchStats.setFormation(formation);

        try {
            teamMatchStatsRepository.save(matchStats);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update match");
        }
    }

    public void updatePlayerStatus(
            Long matchId,
            Long playerId,
            PlayerStatusInMatch startStatus,
            PlayerStatusInMatch endStatus
    ) {

        Match  match = matchRepository.findMatchById(matchId);
        Player player = playerRepository.findPlayerById(playerId);
        PlayerMatchStats stats = playerMatchStatsRepository.findByPlayerAndMatch(player, match)
                .orElseThrow(() -> new RuntimeException("Player stats not found for playerId: " + playerId));

        stats.setStatusAtTheStartOfTheMatch(startStatus);
        stats.setStatusAtTheEndOfTheMatch(endStatus);
        playerMatchStatsRepository.save(stats);
    }

    public void updateFormation(Long matchId, Long clubId, Formation formation) {
        Match match = matchRepository.findMatchById(matchId);
        Club club = clubRepository.findById(clubId);

        if ( match.getHomeClub() == club ) {
            match.getHomeTeamStats().setFormation(formation);
        } else {
            match.getAwayTeamStats().setFormation(formation);
        }

        matchRepository.save(match);
    }

    public void updateStrategy(Long matchId, Long clubId, MatchStrategy strategy) {
        Match match = matchRepository.findMatchById(matchId);
        Club club = clubRepository.findById(clubId);

        if ( match.getHomeClub() == club ) {
            match.getHomeTeamStats().setStrategy(strategy);
        } else {
            match.getAwayTeamStats().setStrategy(strategy);
        }

        matchRepository.save(match);
    }
}
