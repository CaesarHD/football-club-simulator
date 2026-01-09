USE football_club_simulator;

CREATE TABLE transfers
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    new_club_id BIGINT NOT NULL,
    transfer_sum BIGINT DEFAULT 0,
    transfer_status VARCHAR(30) NOT NULL,

    CONSTRAINT fk_transfers_player
        FOREIGN KEY (player_id) REFERENCES players(id),

    CONSTRAINT fk_transfers_new_club
        FOREIGN KEY (new_club_id) REFERENCES clubs(id)
);