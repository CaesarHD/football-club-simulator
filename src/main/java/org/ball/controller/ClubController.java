package org.ball.controller;

import org.ball.entity.Club;
import org.ball.entity.Player;
import org.ball.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    public static Logger log = LoggerFactory.getLogger(ClubController.class);
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("")
    public List<Club> getClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping(params = "name")
    public Club getClubByName(@RequestParam String name) {
       return clubService.getClubByName(name);
    }


}
