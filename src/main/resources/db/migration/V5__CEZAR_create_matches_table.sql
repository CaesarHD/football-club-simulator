use football_club_simulator;

CREATE TABLE matches (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       home_club_id BIGINT NOT NULL,
                       away_club_id BIGINT NOT NULL,
                       home_team_possession INT,
                       away_team_possession INT,
                       home_team_shots INT,
                       away_team_shots INT,
                       home_team_passes INT,
                       away_team_passes INT,
                       home_team_corners INT,
                       away_team_corners INT,
                       home_team_strategy VARCHAR(100) NOT NULL,
                       away_team_strategy VARCHAR(100) NOT NULL,
                       home_team_formation VARCHAR(100) NOT NULL,
                       away_team_formation VARCHAR(100) NOT NULL,
                       date DATETIME,
                       FOREIGN KEY (home_club_id) REFERENCES clubs(id),
                       FOREIGN KEY (away_club_id) REFERENCES clubs(id)
);
