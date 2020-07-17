package com.niewj.config;

import com.niewj.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.niewj.controller", "com.niewj.service"})
public class AutowiredTestConfig {

    // @Primary
    @Bean(name = "productService2")
    public ProductService product2(){
        ProductService productService = new ProductService();
        productService.setLabel("2");
        return productService;
    }
}
