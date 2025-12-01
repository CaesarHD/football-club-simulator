CREATE TABLE team_match_stats (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,

      possession INT DEFAULT 0,
      shots INT DEFAULT 0,
      passes INT DEFAULT 0,
      corners INT DEFAULT 0,

      strategy VARCHAR(100) NOT NULL,
      formation VARCHAR(100) NOT NULL
);

CREATE TABLE matches (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,

     home_club_id BIGINT NOT NULL,
     away_club_id BIGINT NOT NULL,
     date DATETIME,

     home_team_stats_id BIGINT,
     away_team_stats_id BIGINT,

     FOREIGN KEY (home_club_id) REFERENCES clubs(id),
     FOREIGN KEY (away_club_id) REFERENCES clubs(id),
     FOREIGN KEY (home_team_stats_id) REFERENCES team_match_stats(id),
     FOREIGN KEY (away_team_stats_id) REFERENCES team_match_stats(id)
);
