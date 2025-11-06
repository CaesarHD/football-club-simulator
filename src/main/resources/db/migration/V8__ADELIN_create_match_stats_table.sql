Use football_club_simulator;

create table player_match_stats
(
    match_id      BIGINT      not null,
    player_id     BIGINT      not null,
    club_id       BIGINT      not null,
    status varchar(30) null,
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

# INSERT INTO player_match_stats (match_id, player_id, club_id, status, position) VALUES
# -- Liverpool Starters (Club ID: 1, Match ID: 1)
#     (1, 1, 1, 'STARTER', 'GOALKEEPER'),
#     (1, 2, 1, 'STARTER', 'DEFENDER'),
#     (1, 3, 1, 'STARTER', 'DEFENDER'),
#     (1, 4, 1, 'STARTER', 'DEFENDER'),
#     (1, 5, 1, 'STARTER', 'DEFENDER'),
#     (1, 6, 1, 'STARTER', 'MIDFIELDER'),
#     (1, 7, 1, 'STARTER', 'MIDFIELDER'),
#     (1, 8, 1, 'STARTER', 'MIDFIELDER'),
#     (1, 9, 1, 'STARTER', 'FORWARD'),
#     (1, 10, 1, 'STARTER', 'FORWARD'),
#     (1, 11, 1, 'STARTER', 'FORWARD'),
#
#     -- Liverpool Substitutes (Club ID: 1, Match ID: 1)
#     (1, 12, 1, 'SUBSTITUTE', 'GOALKEEPER'),
#     (1, 13, 1, 'SUBSTITUTE', 'DEFENDER'),
#     (1, 14, 1, 'SUBSTITUTE', 'MIDFIELDER'),
#
#     -- Barcelona Starters (Club ID: 2, Match ID: 1)
#     (1, 15, 2, 'STARTER', 'GOALKEEPER'),
#     (1, 16, 2, 'STARTER', 'DEFENDER'),
#     (1, 17, 2, 'STARTER', 'DEFENDER'),
#     (1, 18, 2, 'STARTER', 'DEFENDER'),
#     (1, 19, 2, 'STARTER', 'DEFENDER'),
#     (1, 20, 2, 'STARTER', 'MIDFIELDER'),
#     (1, 21, 2, 'STARTER', 'MIDFIELDER'),
#     (1, 22, 2, 'STARTER', 'MIDFIELDER'),
#     (1, 23, 2, 'STARTER', 'FORWARD'),
#     (1, 24, 2, 'STARTER', 'FORWARD'),
#     (1, 25, 2, 'STARTER', 'FORWARD'),
#
#     -- Barcelona Substitutes (Club ID: 2, Match ID: 1)
#     (1, 26, 2, 'SUBSTITUTE', 'GOALKEEPER'),
#     (1, 27, 2, 'SUBSTITUTE', 'DEFENDER'),
#     (1, 28, 2, 'SUBSTITUTE', 'FORWARD');