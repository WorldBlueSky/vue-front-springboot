create database if not exists vvue;

use vvue;

drop table if exists sys_user;

# 系统用户的表
create table sys_user (
    id int primary key auto_increment comment '用户编号',
    username varchar(50) not null unique  comment '用户名称',
    password varchar(50) not null  comment '用户密码',
    nickname varchar(50) comment '昵称',
    email varchar(50) comment '邮箱',
    phone varchar(20) comment '手机号',
    address varchar(255) comment '地址',
    create_time timestamp default now() comment '保存时间'
);


drop table if exists equipment;

# 设备的信息表
create table equipment(
    id int auto_increment primary key comment '设备编号',
    name varchar(20) unique comment '设备名称',
    typename varchar(20) comment '设备类别',
    number int comment '当前数量',
    unit varchar(20) comment '数量单位',
    location varchar(100) comment '存放地点',
    purchase_Date timestamp comment '购入日期',  # 时间类型
    price double comment '购入单价',
    create_time timestamp default now() comment '保存时间' # 默认时间
);

drop table if exists borrowPeople;

# 申请人员信息表
create table borrowPeople(
    id int auto_increment primary key comment '申请编号',
    name varchar(20)comment '姓名',
    type varchar(20) comment '类别',
    sex varchar(10) comment  '性别',
    age int  comment '年龄',
    phone varchar(20) comment '手机号' ,
    create_time timestamp default now() comment '操作时间'
);
