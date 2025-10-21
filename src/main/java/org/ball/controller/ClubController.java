package org.ball.controller;

import org.ball.entity.Club;
import org.ball.entity.Player;
import org.ball.service.ClubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("")
    public List<Club> getPlayers() {
        return clubService.getAllClubs();
    }

}
