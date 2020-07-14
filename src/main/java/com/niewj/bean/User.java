package com.niewj.bean;

import lombok.Data;

@Data
public class User {
    private String name;
    private String passwd;
    private boolean online ;

    public User(String name, String passwd, boolean online){
        System.out.println("User-初始化!");
        this.name = name;
        this.passwd = passwd;
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", online=" + online +
                '}';
    }
}
