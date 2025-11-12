INSERT INTO clubs (name, budget, stadium_id, country) VALUES ('Liverpool', 250.00);
INSERT INTO clubs (name, budget, stadium_id, country) VALUES ('Barcelona', 200.00);

-- Liverpool Players (club_id = 1)

INSERT INTO players (id, name, age, position, current_club_id)
VALUES (1, 'Alisson', 32, 'GOALKEEPER', 1),
       (2, 'Van Dijk', 33, 'DEFENDER', 1),
       (3, 'Konate', 25, 'DEFENDER', 1),
       (4, 'Robertson', 30, 'DEFENDER', 1),
       (5, 'Alexander-Arnold', 26, 'DEFENDER', 1),
       (6, 'Mac Allister', 25, 'MIDFIELDER', 1),
       (7, 'Szoboszlai', 24, 'MIDFIELDER', 1),
       (8, 'Endo', 31, 'MIDFIELDER', 1),
       (9, 'Salah', 32, 'FORWARD', 1),
       (10, 'Nunez', 25, 'FORWARD', 1),
       (11, 'Diaz', 27, 'FORWARD', 1),
       (12, 'Kelleher', 26, 'GOALKEEPER', 1),
       (13, 'Gomez', 27, 'DEFENDER', 1),
       (14, 'Jones', 23, 'MIDFIELDER', 1);

-- Barcelona Players (club_id = 2)
INSERT INTO players (id, name, age, position, current_club_id)
VALUES (15, 'Ter Stegen', 32, 'GOALKEEPER', 2),
       (16, 'Araujo', 25, 'DEFENDER', 2),
       (17, 'Kounde', 26, 'DEFENDER', 2),
       (18, 'Balde', 21, 'DEFENDER', 2),
       (19, 'Cancelo', 30, 'DEFENDER', 2),
       (20, 'Gavi', 20, 'MIDFIELDER', 2),
       (21, 'Pedri', 22, 'MIDFIELDER', 2),
       (22, 'De Jong', 27, 'MIDFIELDER', 2),
       (23, 'Yamal', 17, 'FORWARD', 2),
       (24, 'Lewandowski', 36, 'FORWARD', 2),
       (25, 'Raphinha', 28, 'FORWARD', 2),
       (26, 'Pena', 25, 'GOALKEEPER', 2),
       (27, 'Martinez', 23, 'DEFENDER', 2),
       (28, 'Torres', 24, 'FORWARD', 2);

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

INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 1,
        '2025-01-11 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 2,
        '2025-03-08 20:00:00');

INSERT INTO player_match_stats (match_id, player_id, club_id, status, position) VALUES
-- Liverpool Starters (Club ID: 1, Match ID: 1)
(1, 1, 1, 'STARTER', 'GOALKEEPER'),
(1, 2, 1, 'STARTER', 'DEFENDER'),
(1, 3, 1, 'STARTER', 'DEFENDER'),
(1, 4, 1, 'STARTER', 'DEFENDER'),
(1, 5, 1, 'STARTER', 'DEFENDER'),
(1, 6, 1, 'STARTER', 'MIDFIELDER'),
(1, 7, 1, 'STARTER', 'MIDFIELDER'),
(1, 8, 1, 'STARTER', 'MIDFIELDER'),
(1, 9, 1, 'STARTER', 'FORWARD'),
(1, 10, 1, 'STARTER', 'FORWARD'),
(1, 11, 1, 'STARTER', 'FORWARD'),

-- Liverpool Substitutes (Club ID: 1, Match ID: 1)
(1, 12, 1, 'SUBSTITUTE', 'GOALKEEPER'),
(1, 13, 1, 'SUBSTITUTE', 'DEFENDER'),
(1, 14, 1, 'SUBSTITUTE', 'MIDFIELDER'),

-- Barcelona Starters (Club ID: 2, Match ID: 1)
(1, 15, 2, 'STARTER', 'GOALKEEPER'),
(1, 16, 2, 'STARTER', 'DEFENDER'),
(1, 17, 2, 'STARTER', 'DEFENDER'),
(1, 18, 2, 'STARTER', 'DEFENDER'),
(1, 19, 2, 'STARTER', 'DEFENDER'),
(1, 20, 2, 'STARTER', 'MIDFIELDER'),
(1, 21, 2, 'STARTER', 'MIDFIELDER'),
(1, 22, 2, 'STARTER', 'MIDFIELDER'),
(1, 23, 2, 'STARTER', 'FORWARD'),
(1, 24, 2, 'STARTER', 'FORWARD'),
(1, 25, 2, 'STARTER', 'FORWARD'),

-- Barcelona Substitutes (Club ID: 2, Match ID: 1)
(1, 26, 2, 'SUBSTITUTE', 'GOALKEEPER'),
(1, 27, 2, 'SUBSTITUTE', 'DEFENDER'),
(1, 28, 2, 'SUBSTITUTE', 'FORWARD');