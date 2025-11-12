use football_club_simulator;

CREATE TABLE matches (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       home_club_id BIGINT NOT NULL,
                       away_club_id BIGINT NOT NULL,
                       date DATETIME,
                       FOREIGN KEY (home_club_id) REFERENCES clubs(id),
                       FOREIGN KEY (away_club_id) REFERENCES clubs(id)
);
