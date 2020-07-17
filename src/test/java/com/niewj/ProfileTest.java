package com.niewj;

import com.alibaba.druid.pool.DruidDataSource;
import com.niewj.config.ProfilesDatasourceConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring profile测试
 */
public class ProfileTest {

    /**
     * 执行时加参数: -Dspring.profiles.active=dev 或者 product
     */
    @Test
    public void testProfileConstructure() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProfilesDatasourceConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("=============================");
        DruidDataSource ds = null;
        if ((ds = ctx.getBean(DruidDataSource.class)) != null) {
            System.out.println(ds);
        }

        ctx.close();
    }

    @Test
    public void testProfile() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ProfilesDatasourceConfig.class);
        ctx.getEnvironment().setActiveProfiles("product"); // 1
//        ctx.getEnvironment().setActiveProfiles("dev"); // 2
        ctx.refresh();

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("=============================");
        DruidDataSource ds = null;
        if ((ds = ctx.getBean(DruidDataSource.class)) != null) {
            System.out.println(ds);
        }

        ctx.close();
    }

}