package com.niewj.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TestDataSource {

    @Value("张三")
    private String name;

    @Value("#{20+3}")
    private int age;

    // 这种竟然都可以!!!
    @Value("#{${ds.age1}+${ds.age2}}")
    private int ageSum;

    @Value("${ds.passwd}")
    private String passwd;

    @Value("${ds.url}")
    private String url;

    public TestDataSource() {
    }

    public TestDataSource(String name, String passwd, String url) {
        System.out.println("User-初始化!");
        this.name = name;
        this.passwd = passwd;
        this.url = url;
    }

}
