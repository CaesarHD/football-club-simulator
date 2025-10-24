use football_club_simulator;


CREATE TABLE clubs (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     budget DECIMAL(10, 2)
);

INSERT INTO clubs (name, budget) VALUES ('Liverpool', 250.00);
INSERT INTO clubs (name, budget) VALUES ('Barcelona', 200.00);
INSERT INTO clubs (name, budget) VALUES ('RealMadrid', 300.00);
INSERT INTO clubs (name, budget) VALUES ('PSG', 350.00);
INSERT INTO clubs (name, budget) VALUES ('ManchesterCity', 320.00);
INSERT INTO clubs (name, budget) VALUES ('Arsenal', 180.00);