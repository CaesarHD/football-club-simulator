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

INSERT INTO players_history (player_id, club_id, no_matches, no_goals, no_assists, joined_date, left_date)
VALUES
-- Liverpool Players (Club ID: 1)
(1, 1, 0, 0, 0, '2018-07-19', NULL),
(2, 1, 0, 0, 0, '2018-01-22', NULL),
(3, 1, 0, 0, 0, '2021-07-01', NULL),
(4, 1, 0, 0, 0, '2017-07-21', NULL),
(5, 1, 0, 0, 0, '2016-11-01', NULL),
(6, 1, 0, 0, 0, '2023-06-08', NULL),
(7, 1, 0, 0, 0, '2023-07-02', NULL),
(8, 1, 0, 0, 0, '2023-08-18', NULL),
(9, 1, 0, 0, 0, '2017-06-22', NULL),
(10, 1, 0, 0, 0, '2022-06-14', NULL),
(11, 1, 0, 0, 0, '2022-01-30', NULL),
(12, 1, 0, 0, 0, '2018-11-01', NULL), -- Sub
(13, 1, 0, 0, 0, '2015-06-10', NULL), -- Sub
(14, 1, 0, 0, 0, '2019-01-01', NULL), -- Sub

-- Barcelona Players (Club ID: 2)
(15, 2, 0, 0, 0, '2014-07-01', NULL),
(16, 2, 0, 0, 0, '2020-10-05', NULL),
(17, 2, 0, 0, 0, '2022-07-29', NULL),
(18, 2, 0, 0, 0, '2021-08-01', NULL),
(19, 2, 0, 0, 0, '2023-09-02', NULL),
(20, 2, 0, 0, 0, '2021-08-15', NULL),
(21, 2, 0, 0, 0, '2020-09-01', NULL),
(22, 2, 0, 0, 0, '2019-07-01', NULL),
(23, 2, 0, 0, 0, '2023-09-01', NULL),
(24, 2, 0, 0, 0, '2022-07-13', NULL),
(25, 2, 0, 0, 0, '2022-07-13', NULL),
(26, 2, 0, 0, 0, '2021-07-01', NULL), -- Sub
(27, 2, 0, 0, 0, '2022-07-01', NULL), -- Sub
(28, 2, 0, 0, 0, '2022-01-01', NULL); -- Sub