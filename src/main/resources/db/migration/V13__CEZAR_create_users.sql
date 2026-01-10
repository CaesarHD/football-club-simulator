create table users
(
    id       BIGINT auto_increment
        primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    role     varchar(20)  not null
);

