package org.ball.tcp;

import org.ball.domain.*;
import org.ball.service.ClubService;
import org.ball.service.CoachService;
import org.ball.service.ManagerService;
import org.ball.service.PlayerService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TcpRouter {

    private final ClubService clubService;
    private final PlayerService playerService;
    private final CoachService coachService;
    private final ManagerService managerService;

    public TcpRouter(ClubService clubService, PlayerService playerService, CoachService coachService, ManagerService managerService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.coachService = coachService;
        this.managerService = managerService;
    }

    private static java.util.Map<String, String> parseKv(String s) {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        if (s == null || s.isBlank()) return map;

        String[] parts = s.split(";");
        for (String part : parts) {
            String[] kv = part.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim().toLowerCase(), kv[1].trim());
            }
        }
        return map;
    }

    private static int parseInt(java.util.Map<String, String> m, String key) {
        String v = m.get(key);
        if (v == null) throw new IllegalArgumentException("missing " + key);
        return Integer.parseInt(v);
    }

    private static String parseStr(java.util.Map<String, String> m, String key) {
        String v = m.get(key);
        if (v == null || v.isBlank()) throw new IllegalArgumentException("missing " + key);
        return v;
    }


    public String handle(String raw) {
        //COMMAND|arg
        String[] p = raw.split("\\|", 2);
        String cmd = p[0];
        String args = (p.length == 2) ? p[1] : "";

        return switch (cmd) {
            case "PING" -> "PONG";
            case "CLUB" -> {
                if (p.length < 2) yield "ERR missing club name";
                Club c = clubService.getClubByName(p[1]);
                if (c == null) yield "NOT_FOUND";

                yield c.toString();
            }
            case "PLAYER" -> {
                if (p.length < 2) yield "ERR missing player name";
                Player player = playerService.getPlayerByName(p[1]);
                if (player == null) yield "NOT_FOUND";
                yield player.toString();
            }
            case "COACH" -> {
                if (p.length < 2) yield "ERR missing coach name";
                Coach coach = coachService.getCoachByName(p[1]);
                if (coach == null) yield "NOT_FOUND";
                yield coach.toString();
            }
            case "MANAGER" -> {
                if (p.length < 2) yield "ERR missing manager name";
                Manager manager = managerService.getManagerByName(p[1]);
                if (manager == null) yield "NOT_FOUND";
                yield manager.toString();
            }

            case "CREATE_PLAYER" -> {
                try {
                    Map<String, String> kv = parseKv(args);

                    String name = parseStr(kv, "name");
                    int age = parseInt(kv, "age");
                    String posStr = parseStr(kv, "position");
                    String clubName = parseStr(kv, "club");
                    Club club = clubService.getClubByName(clubName);
                    if (club == null) yield "ERR club not found: " + clubName;

                    Position position;
                    try {
                        position = Position.valueOf(posStr.toUpperCase());
                    } catch (Exception e) {
                        yield "ERR invalid position. Use: FORWARD|MIDFIELDER|DEFENDER|GOALKEEPER";
                    }

                    Player player = new Player(name, age, position);
                    player.setClub(club);
                    playerService.savePlayer(player);

                    yield "OK created player: " + name;
                } catch (Exception e) {
                    e.printStackTrace();
                    yield "ERR " + e.getClass().getSimpleName() + ": " + e.getMessage();

                }
            }

            case "CREATE_COACH" -> {
                try {
                    Map<String, String> kv = parseKv(args);

                    String name = parseStr(kv, "name");
                    int age = parseInt(kv, "age");

                    String clubName = parseStr(kv, "club");

                    Club club = clubService.getClubByName(clubName);
                    if (club == null) yield "ERR club not found: " + clubName;

                    Coach coach = new Coach(name, age);

                    coach.setClub(club);
                    coachService.saveCoach(coach);

                    yield "OK created coach: " + name;
                } catch (Exception e) {
                    yield "ERR " + e.getMessage();
                }
            }

            case "CREATE_MANAGER" -> {
                try {
                    Map<String, String> kv = parseKv(args);

                    String name = parseStr(kv, "name");
                    int age = parseInt(kv, "age");
                    String clubName = parseStr(kv, "club");

                    Club club = clubService.getClubByName(clubName);
                    if (club == null) yield "ERR club not found: " + clubName;

                    Manager manager = new Manager(name, age);
                    manager.setClub(club);

                    managerService.saveManager(manager);

                    yield "OK created manager: " + name;
                } catch (Exception e) {
                    yield "ERR " + e.getMessage();
                }
            }

            default -> "ERR unknown command";
        };
    }
}
