Use
football_club_simulator;

alter table players
    add current_club_id BIGINT null;

alter table players
    add constraint current_club_id
        foreign key (current_club_id) references clubs (id);

