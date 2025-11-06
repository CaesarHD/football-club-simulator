Use
football_club_simulator;

alter table players
    add current_club_id BIGINT null;

alter table players
    add constraint current_club_id
        foreign key (current_club_id) references clubs (id);
-- Liverpool Players (club_id = 1)

INSERT INTO players (id, name, age, position, current_club_id)
VALUES (1, 'Alisson', 32, 'GOALKEEPER', 1),
       (2, 'Van Dijk', 33, 'DEFENDER', 1),
       (3, 'Konate', 25, 'DEFENDER', 1),
       (4, 'Robertson', 30, 'DEFENDER', 1),
       (5, 'Alexander-Arnold', 26, 'DEFENDER', 1),
       (6, 'Mac Allister', 25, 'MIDFIELDER', 1),
       (7, 'Szoboszlai', 24, 'MIDFIELDER', 1),
       (8, 'Endo', 31, 'MIDFIELDER', 1),
       (9, 'Salah', 32, 'FORWARD', 1),
       (10, 'Nunez', 25, 'FORWARD', 1),
       (11, 'Diaz', 27, 'FORWARD', 1),
       (12, 'Kelleher', 26, 'GOALKEEPER', 1),
       (13, 'Gomez', 27, 'DEFENDER', 1),
       (14, 'Jones', 23, 'MIDFIELDER', 1);

-- Barcelona Players (club_id = 2)
INSERT INTO players (id, name, age, position, current_club_id)
VALUES (15, 'Ter Stegen', 32, 'GOALKEEPER', 2),
       (16, 'Araujo', 25, 'DEFENDER', 2),
       (17, 'Kounde', 26, 'DEFENDER', 2),
       (18, 'Balde', 21, 'DEFENDER', 2),
       (19, 'Cancelo', 30, 'DEFENDER', 2),
       (20, 'Gavi', 20, 'MIDFIELDER', 2),
       (21, 'Pedri', 22, 'MIDFIELDER', 2),
       (22, 'De Jong', 27, 'MIDFIELDER', 2),
       (23, 'Yamal', 17, 'FORWARD', 2),
       (24, 'Lewandowski', 36, 'FORWARD', 2),
       (25, 'Raphinha', 28, 'FORWARD', 2),
       (26, 'Pena', 25, 'GOALKEEPER', 2),
       (27, 'Martinez', 23, 'DEFENDER', 2),
       (28, 'Torres', 24, 'FORWARD', 2);
