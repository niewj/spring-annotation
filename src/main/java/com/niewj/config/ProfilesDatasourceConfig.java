package com.niewj.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.niewj.bean.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

//@Profile("dev") // -Dspring.profiles.active=dev时, 类里所有Bean才有可能注册
@Configuration
@PropertySource("classpath:/db.properties")
public class ProfilesDatasourceConfig {

    {
        System.out.println("ProfilesDatasourceConfig-方法块执行~~");
    }
    /**
     * product 配置
     */
    @Value("${product.mysql.url}")
    private String urlProduct;
    @Value("${product.mysql.username}")
    private String usernameProduct;
    @Value("${product.mysql.password}")
    private String passwordProduct;
    /**
     * dev 配置
     */
    @Value("${dev.mysql.url}")
    private String urlDev;
    @Value("${dev.mysql.username}")
    private String usernameDev;
    @Value("${dev.mysql.password}")
    private String passwordDev;

    /**
     * 其他通用配置
     */
    @Value("${dev.mysql.validationQuery}")
    private String validationQuery = "SELECT 1";

    @Bean
    public Integer value(){
        return new Integer(22);
    }

    @Profile("dev")
    @Bean
    public DruidDataSource dataSourceDev(){
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(urlDev);
        ds.setUsername(usernameDev);
        ds.setPassword(passwordDev);
        ds.setValidationQuery(validationQuery);
        System.out.println("初始化-dev-druidDataSource");

        return ds;
    }

    @Profile("product")
    @Bean
    public DruidDataSource dataSourceProduct(){
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(urlProduct);
        ds.setUsername(usernameProduct);
        ds.setPassword(passwordProduct);
        ds.setValidationQuery(validationQuery);

        System.out.println("初始化-product-druidDataSource");
        return ds;
    }
}
