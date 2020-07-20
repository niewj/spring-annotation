package com.niewj;

import com.niewj.config.TxConfig;
import com.niewj.service.TUserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * spring 声明式事务-测试
 */
public class TxTest {

    @Test
    public void testTx() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TxConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("=============================");

        TUserService userService = ctx.getBean(TUserService.class);
        userService.insertUser();

        ctx.close();
    }

}