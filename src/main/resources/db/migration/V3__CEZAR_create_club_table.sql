use football_simulator;

CREATE TABLE IF NOT EXISTS players
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    coach_id BIGINT NOT NULL,
    manager_id BIGINT NOT NULL,
    stadium_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);


