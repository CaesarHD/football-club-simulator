use football_club_simulator;

CREATE TABLE goals
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    minute    INT NOT NULL,
    player_id BIGINT,
    id_match  BIGINT,
    goal_type VARCHAR(50),
    CONSTRAINT fk_goal_player
        FOREIGN KEY (player_id)
            REFERENCES players (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT fk_goal_match
        FOREIGN KEY (id_match)
            REFERENCES matches (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
