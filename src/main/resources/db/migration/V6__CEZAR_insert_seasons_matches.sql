use football_club_simulator;

-- SEASON 1 (2022)
INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 1,
        '2022-01-15 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 0, 3,
        '2022-03-10 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 1,
        '2022-02-05 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 0,
        '2022-04-02 17:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'PSG'), 3, 2,
        '2022-02-25 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 2,
        '2022-04-30 18:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 1, 0,
        '2022-03-18 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 1,
        '2022-05-15 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 1,
        '2022-06-01 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 2,
        '2022-07-10 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 2, 2,
        '2022-01-25 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 1,
        '2022-03-20 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 1,
        '2022-04-15 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 0,
        '2022-05-12 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 3,
        '2022-06-20 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 1, 1,
        '2022-08-10 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 2, 0,
        '2022-09-15 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 0, 3,
        '2022-10-10 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 0,
        '2022-02-12 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 2, 2,
        '2022-03-25 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 1,
        '2022-05-03 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 2,
        '2022-06-30 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 1,
        '2022-07-25 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 0, 2,
        '2022-08-30 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 1, 1,
        '2022-09-05 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'PSG'), 3, 2,
        '2022-10-15 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 4, 0,
        '2022-11-02 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 3,
        '2022-12-05 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 2, 0,
        '2022-11-20 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 0, 1,
        '2022-12-18 18:00:00');


-- SEASON 2 (2023)
INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 2,
        '2023-01-12 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 2,
        '2023-03-09 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 2,
        '2023-02-05 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 1,
        '2023-04-08 17:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 0,
        '2023-02-22 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 3,
        '2023-05-06 18:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 1,
        '2023-03-16 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 3, 1,
        '2023-05-28 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 1, 0,
        '2023-06-01 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 0, 2,
        '2023-07-09 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 2, 3,
        '2023-01-24 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 0,
        '2023-03-18 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 2,
        '2023-04-14 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 2,
        '2023-05-10 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 0, 3,
        '2023-06-20 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 1,
        '2023-08-08 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 2, 1,
        '2023-09-15 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 1, 1,
        '2023-10-09 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 2,
        '2023-02-10 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 0,
        '2023-03-22 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 3, 1,
        '2023-05-04 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 3,
        '2023-06-25 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 4, 1,
        '2023-07-22 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 0, 2,
        '2023-08-28 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 1,
        '2023-09-06 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 2,
        '2023-10-16 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 0,
        '2023-11-03 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'PSG'), 0, 1,
        '2023-12-04 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 1, 0,
        '2023-11-22 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 3,
        '2023-12-17 18:00:00');

/* ================================
   🏆 SEASON 3 (2024)
   ================================ */
INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 1, 2,
        '2024-01-14 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 3,
        '2024-03-07 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 2, 0,
        '2024-02-03 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 1,
        '2024-04-06 17:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'PSG'), 3, 3,
        '2024-02-26 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 0, 2,
        '2024-05-04 18:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 1,
        '2024-03-15 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 2,
        '2024-05-25 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 4, 1,
        '2024-06-02 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 3,
        '2024-07-12 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 3, 3,
        '2024-01-28 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 0,
        '2024-03-24 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 2,
        '2024-04-10 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 1,
        '2024-05-15 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 0, 1,
        '2024-06-21 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 0,
        '2024-08-10 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 2, 1,
        '2024-09-12 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 1, 2,
        '2024-10-07 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 1,
        '2024-02-11 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 2, 0,
        '2024-03-26 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 3, 2,
        '2024-05-02 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 1,
        '2024-06-27 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 4, 0,
        '2024-07-21 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 3,
        '2024-08-31 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 1, 1,
        '2024-09-09 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 3,
        '2024-10-20 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 0,
        '2024-11-04 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 2,
        '2024-12-05 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 1, 0,
        '2024-11-23 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 0, 2,
        '2024-12-16 18:00:00');


/* ================================
   🏆 SEASON 4 (2025)
   ================================ */
INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 1,
        '2025-01-11 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 2,
        '2025-03-08 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 3, 2,
        '2025-02-01 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 0, 1,
        '2025-04-05 17:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 1,
        '2025-02-20 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 2, 2,
        '2025-05-03 18:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 0,
        '2025-03-19 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 1, 3,
        '2025-05-18 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Liverpool'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 0,
        '2025-06-04 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Liverpool'), 0, 2,
        '2025-07-08 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 3,
        '2025-01-26 21:00:00'),
       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 2,
        '2025-03-23 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 1,
        '2025-04-11 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 3, 0,
        '2025-05-09 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 1, 2,
        '2025-06-19 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 2, 1,
        '2025-08-09 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'Barcelona'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 0,
        '2025-09-14 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'Barcelona'), 1, 1,
        '2025-10-08 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'PSG'), 2, 1,
        '2025-02-09 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 2,
        '2025-03-27 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 3, 2,
        '2025-05-01 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 0, 1,
        '2025-06-28 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'RealMadrid'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 2, 0,
        '2025-07-19 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'RealMadrid'), 1, 3,
        '2025-08-30 19:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 2, 2,
        '2025-09-08 20:00:00'),
       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'PSG'), 1, 2,
        '2025-10-18 21:00:00'),

       ((SELECT id FROM clubs WHERE name = 'PSG'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 4, 1,
        '2025-11-01 18:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'PSG'), 0, 3,
        '2025-12-03 20:00:00'),

       ((SELECT id FROM clubs WHERE name = 'ManchesterCity'), (SELECT id FROM clubs WHERE name = 'Arsenal'), 3, 0,
        '2025-11-21 19:00:00'),
       ((SELECT id FROM clubs WHERE name = 'Arsenal'), (SELECT id FROM clubs WHERE name = 'ManchesterCity'), 1, 2,
        '2025-12-19 18:00:00');
