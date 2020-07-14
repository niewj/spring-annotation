package com.niewj.config;

import com.niewj.bean.LifeTestBean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Life1Config {

    @Bean(value = "lifeBean1", initMethod = "init", destroyMethod = "close")
    public LifeTestBean1 lifeTestBean(){
        return  new LifeTestBean1("bean1");
    }
}
