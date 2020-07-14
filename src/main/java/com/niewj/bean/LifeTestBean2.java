package com.niewj.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 实现 InitializingBean && DisposableBean
 */
public class LifeTestBean2 implements InitializingBean, DisposableBean {
    private String value;

    public LifeTestBean2(String value){
        System.out.println("LifeTestBean2-初始化!");
        this.value = value;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeTestBean2#DisposableBean#destroy-调用!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeTestBean2#InitializingBean#afterPropertiesSet-调用!");
    }
}