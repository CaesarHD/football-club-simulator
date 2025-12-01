use football_club_simulator;

INSERT INTO stadiums (name, capacity) VALUES ('Anfield', 80.000);
INSERT INTO stadiums (name, capacity) VALUES ('Camp Nou', 100.000);

INSERT INTO clubs (name, budget, stadium_id, country) VALUES ('Liverpool', 250.00, 1, 'England');
INSERT INTO clubs (name, budget, stadium_id, country) VALUES ('Barcelona', 200.00, 2, 'Spain');

-- Liverpool Players (club_id = 1)

INSERT INTO players (id, name, age, current_club_id)
VALUES
    (1, 'Alisson', 32, 1),
    (2, 'Van Dijk', 33, 1),
    (3, 'Konate', 25, 1),
    (4, 'Robertson', 30, 1),
    (5, 'Alexander-Arnold', 26, 1),
    (6, 'Mac Allister', 25, 1),
    (7, 'Szoboszlai', 24, 1),
    (8, 'Endo', 31, 1),
    (9, 'Salah', 32, 1),
    (10, 'Nunez', 25, 1),
    (11, 'Diaz', 27, 1),
    (12, 'Kelleher', 26, 1),
    (13, 'Gomez', 27, 1),
    (14, 'Jones', 23, 1);

INSERT INTO player_skills (player_id, position)
VALUES
    (1, 'GOALKEEPER'),
    (2, 'DEFENDER'),
    (3, 'DEFENDER'),
    (4, 'DEFENDER'),
    (5, 'DEFENDER'),
    (6, 'MIDFIELDER'),
    (7, 'MIDFIELDER'),
    (8, 'MIDFIELDER'),
    (9, 'FORWARD'),
    (10, 'FORWARD'),
    (11, 'FORWARD'),
    (12, 'GOALKEEPER'),
    (13, 'DEFENDER'),
    (14, 'MIDFIELDER');



-- Barcelona Players (club_id = 2)
INSERT INTO players (id, name, age, current_club_id)
VALUES
    (15, 'Ter Stegen', 32, 2),
    (16, 'Araujo', 25, 2),
    (17, 'Kounde', 26, 2),
    (18, 'Balde', 21, 2),
    (19, 'Cancelo', 30, 2),
    (20, 'Gavi', 20, 2),
    (21, 'Pedri', 22, 2),
    (22, 'De Jong', 27, 2),
    (23, 'Yamal', 17, 2),
    (24, 'Lewandowski', 36, 2),
    (25, 'Raphinha', 28, 2),
    (26, 'Pena', 25, 2),
    (27, 'Martinez', 23, 2),
    (28, 'Torres', 24, 2);

INSERT INTO player_skills (player_id, position)
VALUES
    (15, 'GOALKEEPER'),
    (16, 'DEFENDER'),
    (17, 'DEFENDER'),
    (18, 'DEFENDER'),
    (19, 'DEFENDER'),
    (20, 'MIDFIELDER'),
    (21, 'MIDFIELDER'),
    (22, 'MIDFIELDER'),
    (23, 'FORWARD'),
    (24, 'FORWARD'),
    (25, 'FORWARD'),
    (26, 'GOALKEEPER'),
    (27, 'DEFENDER'),
    (28, 'FORWARD');


INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
-- Liverpool Players (Club ID: 1)
(1, 1, 0, 0, 0, '2018-07-19', NULL),
(2, 1, 0, 0, 0, '2018-01-22', NULL),
(3, 1, 0, 0, 0, '2021-07-01', NULL),
(4, 1, 0, 0, 0, '2017-07-21', NULL),
(5, 1, 0, 0, 0, '2016-11-01', NULL),
(6, 1, 0, 0, 0, '2023-06-08', NULL),
(7, 1, 0, 0, 0, '2023-07-02', NULL),
(8, 1, 0, 0, 0, '2023-08-18', NULL),
(9, 1, 0, 0, 0, '2017-06-22', NULL),
(10, 1, 0, 0, 0, '2022-06-14', NULL),
(11, 1, 0, 0, 0, '2022-01-30', NULL),
(12, 1, 0, 0, 0, '2018-11-01', NULL), -- Sub
(13, 1, 0, 0, 0, '2015-06-10', NULL), -- Sub
(14, 1, 0, 0, 0, '2019-01-01', NULL), -- Sub

-- Barcelona Players (Club ID: 2)
(15, 2, 0, 0, 0, '2014-07-01', NULL),
(16, 2, 0, 0, 0, '2020-10-05', NULL),
(17, 2, 0, 0, 0, '2022-07-29', NULL),
(18, 2, 0, 0, 0, '2021-08-01', NULL),
(19, 2, 0, 0, 0, '2023-09-02', NULL),
(20, 2, 0, 0, 0, '2021-08-15', NULL),
(21, 2, 0, 0, 0, '2020-09-01', NULL),
(22, 2, 0, 0, 0, '2019-07-01', NULL),
(23, 2, 0, 0, 0, '2023-09-01', NULL),
(24, 2, 0, 0, 0, '2022-07-13', NULL),
(25, 2, 0, 0, 0, '2022-07-13', NULL),
(26, 2, 0, 0, 0, '2021-07-01', NULL), -- Sub
(27, 2, 0, 0, 0, '2022-07-01', NULL), -- Sub
(28, 2, 0, 0, 0, '2022-01-01', NULL); -- Sub

INSERT INTO team_match_stats (possession, shots, passes, corners, strategy, formation)
VALUES (40, 10, 350, 5, 'ATTACKING', 'F4_3_3');

INSERT INTO team_match_stats (possession, shots, passes, corners, strategy, formation)
VALUES (60, 12, 480, 7, 'DEFENDING', 'F4_3_1_2');

INSERT INTO matches (
    home_club_id,
    away_club_id,
    date,
    home_team_stats_id,
    away_team_stats_id
)
VALUES (
           (SELECT id FROM clubs WHERE name = 'Liverpool'),
           (SELECT id FROM clubs WHERE name = 'Barcelona'),
           '2025-01-11 18:00:00',
           1,
           2
       );


INSERT INTO player_match_stats (match_id, player_id, club_id, status_start, status_end, position) VALUES
-- Liverpool Starters (Club ID: 1, Match ID: 1)
(1, 1, 1, 'WHOLE_GAME', 'WHOLE_GAME', 'GOALKEEPER'),
(1, 2, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 3, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 4, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 5, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 6, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 7, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 8, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 9, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),
(1, 10, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),
(1, 11, 1, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),

-- Liverpool Substitutes (Club ID: 1, Match ID: 1)
(1, 12, 1, 'SUBSTITUTE', 'SUBSTITUTE', 'GOALKEEPER'),
(1, 13, 1, 'SUBSTITUTE', 'SUBSTITUTE', 'DEFENDER'),
(1, 14, 1, 'SUBSTITUTE', 'SUBSTITUTE', 'MIDFIELDER'),

-- Barcelona Starters (Club ID: 2, Match ID: 1)
(1, 15, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'GOALKEEPER'),
(1, 16, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 17, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 18, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 19, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'DEFENDER'),
(1, 20, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 21, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 22, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'MIDFIELDER'),
(1, 23, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),
(1, 24, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),
(1, 25, 2, 'WHOLE_GAME', 'WHOLE_GAME',  'FORWARD'),

-- Barcelona Substitutes (Club ID: 2, Match ID: 1)
(1, 26, 2, 'SUBSTITUTE', 'SUBSTITUTE',  'GOALKEEPER'),
(1, 27, 2, 'SUBSTITUTE', 'SUBSTITUTE',  'DEFENDER'),
(1, 28, 2, 'SUBSTITUTE', 'SUBSTITUTE', 'FORWARD');



-- GOALS
INSERT INTO goals (minute, club_id, player_id, id_match, goal_type)
VALUES(70, (SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM players WHERE name = 'Lewandowski'), 1, 'REGULAR')