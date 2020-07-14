package com.niewj.bean;

/**
 * @Bean方式制定 initMethod && destroyMethod
 */
public class LifeTestBean1 {
    private String value;

    public LifeTestBean1(String value){
        System.out.println("LifeTestBean1-初始化!");
        this.value = value;
    }

    public void init(){
        System.out.println("LifeTestBean1#init-调用!");
    }

    public void close(){
        System.out.println("LifeTestBean1#close-调用!");
    }
}