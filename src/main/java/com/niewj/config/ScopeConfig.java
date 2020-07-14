package com.niewj.config;

import com.niewj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScopeConfig {

    /**
     * singleton单例默认是先初始化的; prototype 默认是延迟初始化, 只有getBean才会初始化构造!
     * singleton单例的如果想延迟初始化, 可以在@Bean同时加注解@Lazy
     * @return
     */
    @Scope("singleton")
    @Lazy
    @Bean("personJson")
    public Person person() {
        return new Person("json", 22);
    }
}
