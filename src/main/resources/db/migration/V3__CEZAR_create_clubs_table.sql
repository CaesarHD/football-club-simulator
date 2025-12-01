use football_club_simulator;


CREATE TABLE clubs (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     country VARCHAR(100) NOT NULL,
     budget DECIMAL(10, 2),
     stadium_id  BIGINT not null,
     constraint clubs_stadiums_id_fk
         foreign key (stadium_id) references stadiums (id)
);

