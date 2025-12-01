USE football_club_simulator;

create table players_history
(
    id          bigint auto_increment primary key,
    player_id   bigint not null,
    club_id     bigint not null,
    no_matches  int default 0,
    no_goals    int default 0,
    no_assists  int default 0,
    joined_date date   null,
    left_date   date   null,
    constraint fk_players_history_player
        foreign key (player_id) references players (id),
    constraint fk_players_history_club
        foreign key (club_id) references clubs (id)
);

