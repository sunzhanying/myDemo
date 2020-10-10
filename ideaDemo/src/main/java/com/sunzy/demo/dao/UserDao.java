package com.sunzy.demo.dao;

import com.sunzy.demo.beans.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*CREATE TABLE `users` (
        `id` int(64) DEFAULT NULL,
        `userName` varchar(64) DEFAULT NULL COMMENT '名字',
        `passWord` VARCHAR(64) DEFAULT NULL COMMENT 'passWord',
        `user_sex` VARCHAR(64) DEFAULT NULL COMMENT 'user_sex',
        `nick_name` VARCHAR(64) DEFAULT NULL COMMENT 'nick_name',
        `cmos_modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

/**
 * @author sunzy
 * @date 2020/10/10
 */
@Mapper
public interface UserDao {
    List<UserEntity> getAll();

    Integer getCount();

    UserEntity getOne(Long id);
    void insert(UserEntity user);
    void update(UserEntity user);
    void delete(Long id);
}