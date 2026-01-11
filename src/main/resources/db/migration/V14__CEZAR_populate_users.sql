use football_club_simulator;

INSERT INTO users (username, password, role)
VALUES ('alisson', 'alisson123', 'PLAYER'),
       ('vandijk', 'vandijk123', 'PLAYER'),
       ('konate', 'konate123', 'PLAYER'),
       ('robertson', 'robertson123', 'PLAYER'),
       ('alexanderarnold', 'alexanderarnold123', 'PLAYER'),
       ('macallister', 'macallister123', 'PLAYER'),
       ('szoboszlai', 'szoboszlai123', 'PLAYER'),
       ('endo', 'endo123', 'PLAYER'),
       ('salah', 'salah123', 'PLAYER'),
       ('nunez', 'nunez123', 'PLAYER'),
       ('diaz', 'diaz123', 'PLAYER'),
       ('kelleher', 'kelleher123', 'PLAYER'),
       ('gomez', 'gomez123', 'PLAYER'),
       ('jones', 'jones123', 'PLAYER');

UPDATE players p
    JOIN users u
    ON LOWER(REPLACE(p.name, ' ', '')) = u.username
SET p.user_id = u.id
WHERE p.id BETWEEN 1 AND 14;

INSERT INTO users (username, password, role)
VALUES ('terstegen', 'terstegen123', 'PLAYER'),
       ('araujo', 'araujo123', 'PLAYER'),
       ('kounde', 'kounde123', 'PLAYER'),
       ('balde', 'balde123', 'PLAYER'),
       ('cancelo', 'cancelo123', 'PLAYER'),
       ('gavi', 'gavi123', 'PLAYER'),
       ('pedri', 'pedri123', 'PLAYER'),
       ('dejong', 'dejong123', 'PLAYER'),
       ('yamal', 'yamal123', 'PLAYER'),
       ('lewandowski', 'lewandowski123', 'PLAYER'),
       ('raphinha', 'raphinha123', 'PLAYER'),
       ('pena', 'pena123', 'PLAYER'),
       ('martinez', 'martinez123', 'PLAYER'),
       ('torres', 'torres123', 'PLAYER');

UPDATE players p
    JOIN users u
    ON LOWER(REPLACE(p.name, ' ', '')) = u.username
SET p.user_id = u.id
WHERE p.id BETWEEN 15 AND 28;


INSERT INTO coaches (club_id, name, age, salary)
VALUES
    (1, 'Arne Slot', 46, 80000),
    (2, 'Xavi Hernandez', 45, 90000);

INSERT INTO users (username, password, role)
VALUES
    ('arneslot', 'arneslot123', 'COACH'),
    ('xavi', 'xavi123', 'COACH');

UPDATE coaches c
    JOIN users u
    ON LOWER(REPLACE(c.name, ' ', '')) = u.username
SET c.user_id = u.id;


INSERT INTO managers (club_id, name, age)
VALUES
    (1, 'John Smith', 52),
    (2, 'Maria Lopez', 48);

INSERT INTO users (username, password, role)
VALUES
    ('johnsmith', 'johnsmith123', 'MANAGER'),
    ('marialopez', 'marialopez123', 'MANAGER');

UPDATE managers m
    JOIN users u
    ON LOWER(REPLACE(m.name, ' ', '')) = u.username
SET m.user_id = u.id;
