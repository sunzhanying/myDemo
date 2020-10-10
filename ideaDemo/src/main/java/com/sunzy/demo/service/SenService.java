package com.sunzy.demo.service;

import com.sunzy.demo.beans.SenEntity;
import com.sunzy.demo.dao.SenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunzy
 * @date 2020/10/10
 */
@Service
public class SenService {
    @Autowired
    private SenDao senDao;

    public void insertSen(SenEntity senEntity){
        senDao.insertSen(senEntity);
    }

}
