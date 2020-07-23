package com.niewj.config;

import com.niewj.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.niewj.util")
public class TestBeanCreationConfig {

    @Bean
    public User user() {
        return new User("u", "122", false);
    }
}
