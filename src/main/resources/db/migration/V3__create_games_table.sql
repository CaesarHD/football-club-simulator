use football_club_simulator;

CREATE TABLE games (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    home_club_id BIGINT NOT NULL,
    away_club_id BIGINT NOT NULL,
    FOREIGN KEY (home_club_id) REFERENCES clubs(id),
    FOREIGN KEY (away_club_id) REFERENCES clubs(id)
);