package com.gupaoedu.sca.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDemoDAO {

    @Insert("insert into user_demo(name) values(#{name})")
    int insertUserDemo(String name);

}
/*
create table user_demo
(
    id   int auto_increment
        primary key,
    name char(20) default '' not null
);
 */