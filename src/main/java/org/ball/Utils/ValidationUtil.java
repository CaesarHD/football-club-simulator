package org.ball.Utils;

import org.apache.logging.log4j.util.Strings;
import org.ball.domain.Club;
import org.ball.domain.Goal;
import org.ball.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public class ValidationUtil {

    private static Logger log = LoggerFactory.getLogger(ValidationUtil.class);

    public static void validateDate(LocalDateTime date) {
        if (date == null) {
            String msg = "Date is null";
            log.warn(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public static void validateName(String name) {
        if (Strings.isBlank(name)) {
            String msg = "Name is null or empty";
            log.warn(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public static void validateClub(Club club) {
        if (club == null) {
            String msg = "Club is null";
            log.warn(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public static void validateGoal(Goal goal) {
        if (goal == null) {
            String msg = "Goal is null";
            log.warn(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        //TODO: goal validation
    }

    public static void validateMatch(Match match) {
        if (match == null) {
            String msg = "Match is null";
            log.warn(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }

        Club homeClub = match.getHomeClub();
        Club awayClub = match.getAwayClub();
        validateClub(homeClub);
        validateClub(awayClub);
        validateName(homeClub.getName());
        validateName(awayClub.getName());
        validateDate(match.getDate());
    }
}
