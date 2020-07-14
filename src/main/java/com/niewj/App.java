package com.niewj;

import com.niewj.bean.Person;
import com.niewj.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = ctx.getBean(Person.class);
        System.out.println(person);


        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
