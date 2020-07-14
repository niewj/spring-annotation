package com.niewj;

import com.niewj.bean.TestDataSource;
import com.niewj.config.PropertyReadConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring属性注入/读取
 */
public class PropertyFillTest {

    @Test
    public void testPropety() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyReadConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e-> System.out.println(e));
        System.out.println("=============================");

        TestDataSource ds = ctx.getBean(TestDataSource.class);
        System.out.println(ds);
        ctx.close();
    }

}