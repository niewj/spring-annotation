package com.niewj.bean;

import org.springframework.beans.factory.FactoryBean;

public class Pojo7FactoryBean implements FactoryBean<Pojo7> {
    @Override
    public Pojo7 getObject() throws Exception {
        System.out.println("Pojo7FactoryBean---FactoryBean---初始化");
        return new Pojo7();
    }

    @Override
    public Class<?> getObjectType() {
        return Pojo7.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
