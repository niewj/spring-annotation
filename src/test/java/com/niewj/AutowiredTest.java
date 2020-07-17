package com.niewj;

import com.niewj.config.AutowiredTestConfig;
import com.niewj.controller.ProductController;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring 自动装配
 */
public class AutowiredTest {

    @Test
    public void testAutowired() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AutowiredTestConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");

        ProductController productController = ctx.getBean(ProductController.class);
        productController.doPrint();

        ctx.close();
    }

}