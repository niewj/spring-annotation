package com.niewj.config;

import com.niewj.bean.LifeTestBean3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Life3Config {

    @Bean
    public LifeTestBean3 lifeTestBean() {
        return new LifeTestBean3("bean3");
    }
}