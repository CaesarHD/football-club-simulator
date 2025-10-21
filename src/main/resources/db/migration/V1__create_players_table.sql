Use football_club_simulator;

CREATE TABLE players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NULL,
    position VARCHAR(30) NOT NULL,
    pace INT NULL,
    stamina INT NULL,
    defending INT NULL,
    physical INT NULL,
    dribbling INT NULL,
    shooting INT NULL,
    passing INT NULL
);

INSERT INTO PLAYERS (name, age, position, pace, stamina, defending, physical, dribbling, shooting, passing)
VALUES
    ('Lionel Messi', 37, 'RW', 88, 75, 40, 65, 96, 94, 92),
    ('Andres Iniesta', 41, 'CM', 74, 82, 58, 60, 90, 72, 91),
    ('Xavi Hernandez', 45, 'CM', 65, 83, 65, 64, 84, 70, 93),
    ('Sergio Busquets', 37, 'CDM', 54, 84, 85, 78, 82, 60, 88),
    ('Gerard Pique', 38, 'CB', 56, 70, 88, 85, 65, 55, 75),
    ('Carles Puyol', 47, 'CB', 60, 82, 91, 86, 58, 45, 70),
    ('Dani Alves', 42, 'RB', 86, 90, 80, 76, 85, 70, 85),
    ('Jordi Alba', 36, 'LB', 89, 86, 78, 70, 82, 65, 80),
    ('David Villa', 43, 'ST', 84, 80, 40, 70, 82, 88, 78),
    ('Pedro Rodriguez', 38, 'RW', 85, 82, 50, 66, 84, 81, 78),
    ('Neymar Jr', 33, 'LW', 91, 80, 40, 63, 95, 87, 82),
    ('Luis Suarez', 38, 'ST', 78, 82, 45, 82, 85, 91, 80),
    ('Cesc Fabregas', 38, 'CM', 70, 78, 60, 68, 82, 75, 90),
    ('Marc-Andre ter Stegen', 33, 'GK', 45, 65, 50, 78, 40, 25, 75);


