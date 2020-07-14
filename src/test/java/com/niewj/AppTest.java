package com.niewj;

import com.niewj.bean.Person;
import com.niewj.bean.Pojo7;
import com.niewj.bean.Pojo7FactoryBean;
import com.niewj.bean.User;
import com.niewj.config.ConditionalConfig;
import com.niewj.config.ImportTestConfig;
import com.niewj.config.MainConfig;
import com.niewj.config.ScopeConfig;
import com.niewj.controller.BaseController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void testStream() {
        Random r = new Random();
        long count = Stream.generate(() -> r.nextInt()).limit(10)
                .peek(s -> System.out.println(Thread.currentThread().getName() + " peek: " + s)) // 1
                .filter(s -> {  // 2
                    System.out.println(Thread.currentThread().getName() + " filter: " + s);
                    return s > 1000000;
                })
                .sorted((i1, i2) -> { // 3.
                    System.out.println(Thread.currentThread().getName() + " 排序:" + i1 + ", " + i2);
                    return i1.compareTo(i2);
                })
                .peek(s -> System.out.println(Thread.currentThread().getName() + " peek2:" + s)) //4.
                .parallel()
                .count();
        System.out.println(count);
    }

    private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(clazz);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        return ctx;
    }

    @Test
    public void testImport() {
        // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
        ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
        Pojo7 bean1 = ctx.getBean("pojo7", Pojo7.class);
        System.out.println(bean1);
        Pojo7FactoryBean bean2 = ctx.getBean("&pojo7", Pojo7FactoryBean.class);
        System.out.println(bean2);
    }

    @Test
    public void testClassName() {
        System.out.println(BaseController.class.getSimpleName()); // BaseController
        System.out.println(BaseController.class.getName()); // com.niewj.controller.BaseController
    }

    @Test
    public void testConditinalBean() {
        ApplicationContext ctx = printAnnoBeans(ConditionalConfig.class);

        Environment environment = ctx.getEnvironment();
        String profile = environment.getProperty("spring.profiles.active");
        System.out.println(profile);

        User user = ctx.getBean(User.class);
        System.out.println(user);
    }

    @Test
    public void testScope() {
        AnnotationConfigApplicationContext ctx = printAnnoBeans(ScopeConfig.class);
        Person p = ctx.getBean("personJson", Person.class);
        System.out.println(p);

    }

    @Test
    public void testTypeFilter() {
        AnnotationConfigApplicationContext ctx = printAnnoBeans(MainConfig.class);
    }
}