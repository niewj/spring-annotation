package com.niewj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.UUID;

@Repository("userDao")
public class TUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveUser() {
        String name = UUID.randomUUID().toString().replaceAll("-","");
        int age = new Random().nextInt(35);
        String gender = "male";

        // 这里写了单引号会报错
        String insertSql = "insert into t_user(name, age, gender) values(?, ?, ?)";
        int updated = jdbcTemplate.update(insertSql, name, age, gender);

        // ---------
        doCalc();

        return updated;
    }

    // 做一些运算, 有可能异常, 如除法
    public void doCalc(){
        int value = 20/0;
    }
}
