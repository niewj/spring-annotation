package com.niewj.config;

import com.niewj.bean.TestDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/jdbc.properties")
public class PropertyReadConfig {

    @Bean
    public TestDataSource tDataSource(){
        return new TestDataSource();
    }
}
