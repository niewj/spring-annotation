package com.niewj.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 普通bean+@Component
 */
@Component
public class LifeTestBeanAll implements InitializingBean, DisposableBean {

    public LifeTestBeanAll() {
        System.out.println("LifeTestBeanAll 构造方法调用!");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("LifeTestBeanAll#@PostConstruct 调用");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("LifeTestBeanAll#@PreDestroy 调用");
    }

    public void init(){
        System.out.println("LifeTestBeanAll#@Bean(initMethod) 调用");
    }

    public void close(){
        System.out.println("LifeTestBeanAll#@Bean(destroyMethod) 调用");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeTestBeanAll#Disposable 调用~~");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeTestBeanAll#InitializingBean#调用!~");
    }
}