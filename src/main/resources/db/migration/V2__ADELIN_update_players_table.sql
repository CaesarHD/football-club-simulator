USE football_simulator;

alter table players
    add age int not null;

alter table players
    add position varchar(100) not null;

alter table players
    add rating int null;

alter table players
    add crt_club_id int null;

alter table players
    add crt_salary int null;