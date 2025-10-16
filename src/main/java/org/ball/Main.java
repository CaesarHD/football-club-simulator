package org.ball;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@SpringBootApplication
public class Main {

    private final PlayerRepository playerRepository;

    public Main(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {
        playerRepository.save(new Player("Cezar"));
        System.out.println(playerRepository.findAll());
    }
}