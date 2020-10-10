package com.sunzy.demo.dao;

import com.sunzy.demo.beans.SenEntity;
import com.sunzy.demo.beans.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*CREATE TABLE `sen` (
  `id` INT(64) NOT NULL AUTO_INCREMENT,
  `beginDate` VARCHAR(64) DEFAULT NULL COMMENT 'beginDate',
  `code` VARCHAR(64) DEFAULT NULL COMMENT 'code',
  `name` VARCHAR(200) DEFAULT NULL COMMENT 'user_sex',
  `phone` VARCHAR(64) DEFAULT NULL COMMENT 'nick_name',
  `address` VARCHAR(500) DEFAULT NULL COMMENT 'nick_name',
  `cmos_modify_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
   PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;*/

/**
 * @author sunzy
 * @date 2020/10/10
 */
@Mapper
public interface SenDao {
    void insertSen(SenEntity senEntity);
}