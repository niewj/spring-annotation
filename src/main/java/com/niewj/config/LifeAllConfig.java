package com.niewj.config;

import com.niewj.bean.LifeTestBeanAll;
import com.niewj.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.niewj.bean")
public class LifeAllConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public LifeTestBeanAll lifeTestBeanAll(){
        return new LifeTestBeanAll();
    }
}