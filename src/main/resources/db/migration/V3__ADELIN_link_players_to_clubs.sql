Use football_club_simulator;

alter table players
    add current_club_id int null;

alter table players
    add constraint current_club_id
        foreign key (current_club_id) references clubs (id);

INSERT INTO players (name, age, position, reflexes, diving, physical, passing, current_club_id)
VALUES
    ('Víctor Valdés', 43, 'GOALKEEPER', 88, 85, 70, 65, 2),
    ('Alisson Becker', 32, 'GOALKEEPER', 91, 89, 78, 80, 1);

INSERT INTO players (name, age, position, defending, physical, pace, passing, stamina, current_club_id)
VALUES
    ('Carles Puyol', 47, 'DEFENDER', 94, 90, 70, 75, 85, 2),
    ('Gerard Piqué', 38, 'DEFENDER', 90, 85, 68, 80, 78, 2),
    ('Virgil van Dijk', 34, 'DEFENDER', 94, 92, 78, 83, 84, 1),
    ('Jamie Carragher', 47, 'DEFENDER', 88, 86, 65, 75, 80, 1);

INSERT INTO players (name, age, position, passing, stamina, dribbling, defending, current_club_id)
VALUES
    ('Xavi Hernández', 45, 'MIDFIELDER', 96, 92, 88, 70, 2),
    ('Andrés Iniesta', 41, 'MIDFIELDER', 94, 90, 95, 68, 2),
    ('Steven Gerrard', 45, 'MIDFIELDER', 90, 88, 86, 78, 1),
    ('Xabi Alonso', 43, 'MIDFIELDER', 92, 86, 82, 80, 1);

INSERT INTO players (name, age, position, pace, dribbling, shooting, passing, stamina, current_club_id)
VALUES
    ('Lionel Messi', 38, 'FORWARD', 87, 99, 95, 93, 82, 2),
    ('Luis Suárez', 38, 'FORWARD', 80, 86, 92, 85, 78, 2),
    ('Mohamed Salah', 33, 'FORWARD', 96, 90, 91, 83, 85, 1),
    ('Sadio Mané', 33, 'FORWARD', 94, 88, 89, 80, 88, 1);

INSERT INTO players (name, age, position, pace, dribbling, shooting, passing, stamina)
VALUES
    ('Adelin Vancea', 20, 'FORWARD', 87, 99, 95, 93, 82);
