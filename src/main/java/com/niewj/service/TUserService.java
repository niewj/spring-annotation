package com.niewj.service;

import com.niewj.dao.TUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class TUserService {

    @Autowired
    private TUserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.saveUser();
    }
}
