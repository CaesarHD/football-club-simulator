use football_club_simulator;


create table managers
(
    id      BIGINT       not null
        primary key,
    name    varchar(100) null,
    age     int          null,
    club_id BIGINT       null,
    constraint managers_clubs_id_fk
        foreign key (club_id) references clubs (id)
);
