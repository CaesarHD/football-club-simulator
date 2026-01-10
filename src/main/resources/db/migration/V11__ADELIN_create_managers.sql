use football_club_simulator;


create table managers
(
    id      BIGINT AUTO_INCREMENT not null
        primary key,
    name    varchar(100) null,
    age     int          null,
    club_id BIGINT       not null,
    constraint managers_clubs_id_fk
        foreign key (club_id) references clubs (id)
);
