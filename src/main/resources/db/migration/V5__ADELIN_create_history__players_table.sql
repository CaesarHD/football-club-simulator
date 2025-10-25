USE football_club_simulator;

CREATE TABLE PLAYERS_HISTORY (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     player_id BIGINT NOT NULL,
     club_id   BIGINT NOT NULL,
     no_matches   INT DEFAULT 0,
     no_goals     INT DEFAULT 0,
     no_assists   INT DEFAULT 0,
     joined_date DATE NULL,
     left_date   DATE NULL,
     CONSTRAINT FK_PLAYERS_HISTORY_PLAYER
         FOREIGN KEY (player_id) REFERENCES PLAYERS (id),
     CONSTRAINT FK_PLAYERS_HISTORY_CLUB
         FOREIGN KEY (club_id) REFERENCES CLUBS (id)
);


INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
    (1, 2, 535, 0, 2, '2002-08-01', '2014-06-01'),
    (2, 1, 260, 0, 5, '2018-07-01', NULL);

INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
    (3, 2, 392, 12, 15, '1999-07-01', '2014-06-01'),
    (4, 2, 615, 52, 25, '2008-07-01', '2022-06-01'),
    (5, 1, 275, 25, 10, '2018-01-01', NULL),
    (6, 1, 737, 5, 15, '1997-08-01', '2013-05-31');

INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
    (7, 2, 767, 85, 185, '1998-07-01', '2015-06-01'),
    (8, 2, 674, 57, 138, '2002-07-01', '2018-06-01'),
    (9, 1, 710, 185, 155, '1998-07-01', '2015-06-01'),
    (10, 1, 210, 18, 40, '2004-08-01', '2009-06-01'),
    (10, 3, 236, 9, 36, '2009-07-01', '2014-06-01');

INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
    (11, 2, 778, 672, 268, '2004-10-16', '2021-08-10'),
    (12, 2, 283, 198, 113, '2014-07-01', '2020-09-30'),
    (13, 1, 342, 204, 80, '2017-07-01', NULL),
    (14, 1, 269, 120, 58, '2016-07-01', '2022-07-01'),
    (15, 5, 0, 0, 0, '2024-07-01', NULL);