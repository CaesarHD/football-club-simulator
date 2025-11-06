Use football_club_simulator;

create table match_stats
(
    match_id      BIGINT      not null,
    player_id     BIGINT      not null,
    club_id       BIGINT      not null,
    status varchar(30) null,
    position varchar(30) null,
    id            BIGINT auto_increment
        primary key,
    constraint match_stats_clubs_id_fk
        foreign key (club_id) references clubs (id),
    constraint match_stats_matches_id_fk
        foreign key (match_id) references matches (id),
    constraint match_stats_players_id_fk
        foreign key (player_id) references players (id)
);

INSERT INTO match_stats (match_id, player_id, club_id, status, position) VALUES
-- Liverpool Starters (Club ID: 1, Match ID: 1)
    (91, 1, 1, 'STARTER', 'GOALKEEPER'),
    (91, 2, 1, 'STARTER', 'DEFENDER'),
    (91, 3, 1, 'STARTER', 'DEFENDER'),
    (91, 4, 1, 'STARTER', 'DEFENDER'),
    (91, 5, 1, 'STARTER', 'DEFENDER'),
    (91, 6, 1, 'STARTER', 'MIDFIELDER'),
    (91, 7, 1, 'STARTER', 'MIDFIELDER'),
    (91, 8, 1, 'STARTER', 'MIDFIELDER'),
    (91, 9, 1, 'STARTER', 'FORWARD'),
    (91, 10, 1, 'STARTER', 'FORWARD'),
    (91, 11, 1, 'STARTER', 'FORWARD'),

    -- Liverpool Substitutes (Club ID: 1, Match ID: 1)
    (91, 12, 1, 'SUBSTITUTE', 'GOALKEEPER'),
    (91, 13, 1, 'SUBSTITUTE', 'DEFENDER'),
    (91, 14, 1, 'SUBSTITUTE', 'MIDFIELDER'),

    -- Barcelona Starters (Club ID: 2, Match ID: 1)
    (91, 15, 2, 'STARTER', 'GOALKEEPER'),
    (91, 16, 2, 'STARTER', 'DEFENDER'),
    (91, 17, 2, 'STARTER', 'DEFENDER'),
    (91, 18, 2, 'STARTER', 'DEFENDER'),
    (91, 19, 2, 'STARTER', 'DEFENDER'),
    (91, 20, 2, 'STARTER', 'MIDFIELDER'),
    (91, 21, 2, 'STARTER', 'MIDFIELDER'),
    (91, 22, 2, 'STARTER', 'MIDFIELDER'),
    (91, 23, 2, 'STARTER', 'FORWARD'),
    (91, 24, 2, 'STARTER', 'FORWARD'),
    (91, 25, 2, 'STARTER', 'FORWARD'),

    -- Barcelona Substitutes (Club ID: 2, Match ID: 1)
    (91, 26, 2, 'SUBSTITUTE', 'GOALKEEPER'),
    (91, 27, 2, 'SUBSTITUTE', 'DEFENDER'),
    (91, 28, 2, 'SUBSTITUTE', 'FORWARD');