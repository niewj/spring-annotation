package com.niewj.bean;

import org.springframework.stereotype.Component;

/**
 * 普通bean+@Component
 */
@Component
public class LifeTestBean4 {

    public LifeTestBean4() {
        System.out.println("LifeTestBean4-初始化!");
    }
}