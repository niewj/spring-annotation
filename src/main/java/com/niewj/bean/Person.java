package com.niewj.bean;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;

    public Person(String name, int age){
        System.out.println("Person 初始化~~~");
        this.age = age;
        this.name = name;
    }
}
