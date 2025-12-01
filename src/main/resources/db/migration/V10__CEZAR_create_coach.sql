use football_club_simulator;

CREATE TABLE coaches
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    age  INT DEFAULT 30,
    salary FLOAT DEFAULT 0,
    constraint coaches_club_id_fk
        foreign key (club_id) references clubs(id)
);