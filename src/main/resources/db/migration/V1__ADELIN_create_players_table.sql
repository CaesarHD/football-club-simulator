Use football_club_simulator;

CREATE TABLE players
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    age    INT        DEFAULT 18,
    salary FLOAT      DEFAULT  0
);

CREATE TABLE player_skills
(
    position  VARCHAR(30) NOT NULL,
    player_id BIGINT PRIMARY KEY,
    pace      INT DEFAULT 0,
    stamina   INT DEFAULT 0,
    defending INT DEFAULT 0,
    physical  INT DEFAULT 0,
    dribbling INT DEFAULT 0,
    shooting  INT DEFAULT 0,
    passing   INT DEFAULT 0,
    reflexes  INT DEFAULT 0,
    diving    INT DEFAULT 0,
    CONSTRAINT fk_skills_players
        FOREIGN KEY (player_id) REFERENCES players (id)
            ON DELETE CASCADE
);
