Use football_club_simulator;

CREATE TABLE players (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     age INT NULL,
     position VARCHAR(30) NOT NULL,
     pace INT DEFAULT 0,
     stamina INT DEFAULT 0,
     defending INT DEFAULT 0,
     physical INT DEFAULT 0,
     dribbling INT DEFAULT 0,
     shooting INT DEFAULT 0,
     passing INT DEFAULT 0,
     reflexes INT DEFAULT 0,
     diving INT DEFAULT 0
);

INSERT INTO players (name, age, position, reflexes, diving, physical, passing)
VALUES
    ('Víctor Valdés', 43, 'GOALKEEPER', 88, 85, 70, 65),
    ('Alisson Becker', 32, 'GOALKEEPER', 91, 89, 78, 80);

INSERT INTO players (name, age, position, defending, physical, pace, passing, stamina)
VALUES
    ('Carles Puyol', 47, 'DEFENDER', 94, 90, 70, 75, 85),
    ('Gerard Piqué', 38, 'DEFENDER', 90, 85, 68, 80, 78),
    ('Virgil van Dijk', 34, 'DEFENDER', 94, 92, 78, 83, 84),
    ('Jamie Carragher', 47, 'DEFENDER', 88, 86, 65, 75, 80);

INSERT INTO players (name, age, position, passing, stamina, dribbling, defending)
VALUES
    ('Xavi Hernández', 45, 'MIDFIELDER', 96, 92, 88, 70),
    ('Andrés Iniesta', 41, 'MIDFIELDER', 94, 90, 95, 68),
    ('Steven Gerrard', 45, 'MIDFIELDER', 90, 88, 86, 78),
    ('Xabi Alonso', 43, 'MIDFIELDER', 92, 86, 82, 80);

INSERT INTO players (name, age, position, pace, dribbling, shooting, passing, stamina)
VALUES
    ('Lionel Messi', 38, 'FORWARD', 87, 99, 95, 93, 82),
    ('Luis Suárez', 38, 'FORWARD', 80, 86, 92, 85, 78),
    ('Mohamed Salah', 33, 'FORWARD', 96, 90, 91, 83, 85),
    ('Sadio Mané', 33, 'FORWARD', 94, 88, 89, 80, 88);

