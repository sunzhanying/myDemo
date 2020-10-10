package com.sunzy.demo.service;

import com.sunzy.demo.beans.UserEntity;
import com.sunzy.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzy
 * @date 2020/10/10
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<UserEntity>  getAll(){
        List<UserEntity> list = userDao.getAll();
        return list;
    }


    public Integer  getCount(){
        System.out.println("123");
        Integer list = userDao.getCount();
        return list;
        //return null;
    }

}
