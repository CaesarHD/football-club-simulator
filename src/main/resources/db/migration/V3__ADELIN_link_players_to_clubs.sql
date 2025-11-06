Use football_club_simulator;

alter table players
    add current_club_id BIGINT null;

alter table players
    add constraint current_club_id
        foreign key (current_club_id) references clubs (id);

INSERT INTO players (id, name, age, position) VALUES
-- Liverpool Players (IDs 1-14)
(1, 'Alisson', 32, 'GOALKEEPER'),
(2, 'Van Dijk', 33, 'DEFENDER'),
(3, 'Konate', 25, 'DEFENDER'),
(4, 'Robertson', 30, 'DEFENDER'),
(5, 'Alexander-Arnold', 26, 'DEFENDER'),
(6, 'Mac Allister', 25, 'MIDFIELDER'),
(7, 'Szoboszlai', 24, 'MIDFIELDER'),
(8, 'Endo', 31, 'MIDFIELDER'),
(9, 'Salah', 32, 'FORWARD'),
(10, 'Nunez', 25, 'FORWARD'),
(11, 'Diaz', 27, 'FORWARD'),
(12, 'Kelleher', 26, 'GOALKEEPER'), -- Sub
(13, 'Gomez', 27, 'DEFENDER'),     -- Sub
(14, 'Jones', 23, 'MIDFIELDER'),   -- Sub

-- Barcelona Players (IDs 15-28)
(15, 'Ter Stegen', 32, 'GOALKEEPER'),
(16, 'Araujo', 25, 'DEFENDER'),
(17, 'Kounde', 26, 'DEFENDER'),
(18, 'Balde', 21, 'DEFENDER'),
(19, 'Cancelo', 30, 'DEFENDER'),
(20, 'Gavi', 20, 'MIDFIELDER'),
(21, 'Pedri', 22, 'MIDFIELDER'),
(22, 'De Jong', 27, 'MIDFIELDER'),
(23, 'Yamal', 17, 'FORWARD'),
(24, 'Lewandowski', 36, 'FORWARD'),
(25, 'Raphinha', 28, 'FORWARD'),
(26, 'Pena', 25, 'GOALKEEPER'),   -- Sub
(27, 'Martinez', 23, 'DEFENDER'),   -- Sub
(28, 'Torres', 24, 'FORWARD');     -- Sub