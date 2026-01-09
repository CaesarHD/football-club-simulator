USE FOOTBALL_CLUB_SIMULATOR;

CREATE TABLE transfers
(
    id            BIGINT NOT NULL
        PRIMARY KEY,
    player   BIGINT NOT NULL,
    new_club BIGINT NOT NULL,
    transfer_sum  BIGINT NULL,
    transfer_status VARCHAR(30) NOT NULL,
    CONSTRAINT transfer_offers_clubs_id_fk
        FOREIGN KEY (receiver_club) REFERENCES clubs (id),
    CONSTRAINT transfer_sender_clubs_id_fk
        FOREIGN KEY (sender_club) REFERENCES clubs (id)
);



