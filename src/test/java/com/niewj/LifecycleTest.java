package com.niewj;

import com.niewj.bean.*;
import com.niewj.config.Life1Config;
import com.niewj.config.Life2Config;
import com.niewj.config.Life3Config;
import com.niewj.config.Life4Config;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring生命周期.
 */
public class LifecycleTest {

    @Test
    public void testLifecycle4() { //Life4Config.class
        // 1. 通过 实现 BeanPostProcesser 接口
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Life4Config.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");

        ctx.close();
    }

    @Test
    public void testLifecycle3() {
        // 1. 通过 JSR250注解 @PostConstruct && @PreDestroy
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Life3Config.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");
        LifeTestBean3 bean = ctx.getBean(LifeTestBean3.class);

        System.out.println(bean);

        ctx.close();
    }

    @Test
    public void testLifecycle2() {
        // 1. 通过bean实现 InitializingBean和DisposableBean接口
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Life2Config.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");
        LifeTestBean2 bean = ctx.getBean(LifeTestBean2.class);

        System.out.println(bean);

        ctx.close();
    }

    @Test
    public void testLifecycle() {
        // 1. 通过 @Bean注解指定 initMethod && destroyMethod
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Life1Config.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");
        LifeTestBean1 bean = ctx.getBean(LifeTestBean1.class);

        System.out.println(bean);

        ctx.close();
    }
}