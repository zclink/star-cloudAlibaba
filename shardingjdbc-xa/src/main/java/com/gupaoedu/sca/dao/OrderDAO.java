package com.gupaoedu.sca.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDAO {

    @Insert("insert into t_order(user_id,status) values(#{userId},0)")
    int insertUserOne(int userId);

    @Insert("insert into t_order(user_id,status) values(#{userId},0)")
    int insertUserTwo(int userId);

}
/*

CREATE TABLE `t_order`
(
    `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`  int(11)    NOT NULL,
    `status`   varchar(50) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 474585204191657985
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin



 */