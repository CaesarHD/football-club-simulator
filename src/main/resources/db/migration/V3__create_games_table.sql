use football_club_simulator;

CREATE TABLE matches (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       home_club_id BIGINT NOT NULL,
                       away_club_id BIGINT NOT NULL,
                       home_team_score INT,
                       away_team_score INT,
                       date DATETIME,
                       FOREIGN KEY (home_club_id) REFERENCES clubs(id),
                       FOREIGN KEY (away_club_id) REFERENCES clubs(id)
);

INSERT INTO matches (home_club_id, away_club_id, home_team_score, away_team_score, date)
VALUES
    (
        (SELECT id FROM clubs WHERE name = 'Liverpool'),
        (SELECT id FROM clubs WHERE name = 'Barcelona'),
        2,
        1,
        '2025-10-2 15:00:00'
    ),
    (
        (SELECT id FROM clubs WHERE name = 'Real Madrid'),
        (SELECT id FROM clubs WHERE name = 'PSG'),
        0,
        0,
        '2025-10-4 18:00:00'
    ),
    (
        (SELECT id FROM clubs WHERE name = 'Arsenal'),
        (SELECT id FROM clubs WHERE name = 'Manchester City'),
        1,
        3,
        '2025-10-8 20:00:00'
    ),
    (
        (SELECT id FROM clubs WHERE name = 'Real Madrid'),
        (SELECT id FROM clubs WHERE name = 'PSG'),
        NULL,
        NULL,
        '2025-11-01 16:00:00'
    );