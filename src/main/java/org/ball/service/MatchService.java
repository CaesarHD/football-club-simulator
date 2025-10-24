package org.ball.service;

import org.ball.entity.Match;
import org.ball.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;


    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatchesByClubName(String clubName) {
        return matchRepository.findAllMatchesByClub(clubName);
    }
}
