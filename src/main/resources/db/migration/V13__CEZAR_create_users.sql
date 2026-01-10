create table users
(
    id       BIGINT auto_increment
        primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    role     varchar(20)  not null
);

alter table players
    add user_id bigint null;

alter table players
    add constraint players_users_id_fk
        foreign key (user_id) references users (id);

alter table coaches
    add user_id bigint null;

alter table coaches
    add constraint coaches_users_id_fk
        foreign key (user_id) references users (id);


alter table managers
    add user_id bigint null;

alter table managers
    add constraint managers_users_id_fk
        foreign key (user_id) references users (id);

insert into users (username, password, role)
values ('salah', 'parola123', 'PLAYER');

-- leg user-ul de playerul Salah (player id 9)
update players
set user_id = (select id from users where username = 'salah')
where id = 9;