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

