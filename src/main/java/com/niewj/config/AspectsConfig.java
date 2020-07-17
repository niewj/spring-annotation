package com.niewj.config;

import com.niewj.aop.BizRequest;
import com.niewj.aop.BizRequestLogAspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectsConfig {

    @Bean
    public BizRequest bizRequest() {
        return new BizRequest();
    }

    @Bean
    public BizRequestLogAspects bizRequestLogAspects() {
        return new BizRequestLogAspects();
    }
}
