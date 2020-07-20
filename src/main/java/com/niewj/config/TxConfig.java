package com.niewj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ComponentScan({"com.niewj.service", "com.niewj.dao"})
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/db.properties")
public class TxConfig {

    /**
     * dev 配置
     */
    @Value("${dev.mysql.url}")
    private String urlDev;
    @Value("${dev.mysql.username}")
    private String usernameDev;
    @Value("${dev.mysql.password}")
    private String passwordDev;
    @Value("${dev.mysql.connectionProperties}")
    private String connectionProperties;

    // 1. 注册 DataSource 数据源
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(urlDev);
        ds.setUsername(usernameDev);
        ds.setPassword(passwordDev);
        ds.setValidationQuery("SELECT 1");
        ds.setConnectionProperties(connectionProperties);

        System.out.println("初始化-dev-DataSource");
        return ds;
    }

    /**
     * 2. 注册 JdbcTemplate 工具
     * @param dataSource 容器会自动从其内找到bean注入
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
//        return new JdbcTemplate(dataSource()); // 调用也可以, 不会重复创建
        return new JdbcTemplate(dataSource);
    }

    /**
     * 3. 注册 事务管理器 PlatformTransactionManager(DataSourceTransactionManager)
     *  这里使用调用 dataSource()方法, 也可以写入参传入
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
