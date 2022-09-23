create database if not exists vvue;

use vvue;

drop table if exists sys_user;

create table sys_user(
    id int primary key auto_increment,
    username varchar(50) not null ,
    password varchar(50) not null ,
    nickname varchar(50) ,
    email varchar(50) ,
    phone varchar(20),
    address varchar(255),
    create_time timestamp default now()
);
