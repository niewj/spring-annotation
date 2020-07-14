package com.niewj.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 通过 JSR250注解 @PostConstruct && @PreDestroy
 */
public class LifeTestBean3 {
    private String value;

    public LifeTestBean3(String value) {
        System.out.println("LifeTestBean3-初始化!");
        this.value = value;
    }

    @PreDestroy
    public void doDestroy() {
        System.out.println("LifeTestBean3#doDestroy-调用!");
    }

    @PostConstruct
    public void doInit() {
        System.out.println("LifeTestBean3#doInit-调用!");
    }
}