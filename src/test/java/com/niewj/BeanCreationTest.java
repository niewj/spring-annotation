package com.niewj;

import com.niewj.config.TestBeanCreationConfig;
import com.niewj.util.Service1;
import com.niewj.util.Service2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring bean创建和初始化测试:
 *  BeanFactoryPostProcessor
 *  BeamDefinitionRegistryPostProcessor
 */
public class BeanCreationTest {

    @Test
    public void testBeanCreation() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TestBeanCreationConfig.class);
        ctx.refresh();

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("============Service1#doBusiness()=================");

        Service1 service1 = ctx.getBean(Service1.class);
        service1.doBusiness();
        System.out.println("======Service2#doSth()=====");
        Service2 service2 = ctx.getBean(Service2.class);
        service2.doSth();

        ctx.close();
    }
}