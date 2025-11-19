Use football_club_simulator;

create table player_match_stats
(
    match_id      BIGINT      not null,
    player_id     BIGINT      not null,
    club_id       BIGINT      not null,
    status_start varchar(30) null,
    status_end varchar(30) null,
    position varchar(30) null,
    id            BIGINT auto_increment
        primary key,
    constraint player_match_stats_clubs_id_fk
        foreign key (club_id) references clubs (id),
    constraint player_match_stats_matches_id_fk
        foreign key (match_id) references matches (id),
    constraint player_match_stats_players_id_fk
        foreign key (player_id) references players (id)
);

