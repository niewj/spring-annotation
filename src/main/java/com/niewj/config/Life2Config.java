package com.niewj.config;

import com.niewj.bean.LifeTestBean2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Life2Config {

    @Bean
    public LifeTestBean2 lifeTestBean(){
        return  new LifeTestBean2("bean2");
    }
}