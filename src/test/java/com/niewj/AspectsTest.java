package com.niewj;

import com.niewj.aop.BizRequest;
import com.niewj.config.AspectsConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * spring AOP通知测试
 */
public class AspectsTest {

    @Test
    public void testAopReturning() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AspectsConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("=============================");

        // 正常返回的用例:
        BizRequest bizRequest = ctx.getBean(BizRequest.class);
        Map<String, String> params = new HashMap<>();
        params.put("name", "niewj");
        params.put("idCard", "142525199905051111");
        params.put("phone", "15215152525");

        bizRequest.callData(params);
        ctx.close();
    }

    @Test
    public void testAopThrowing() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AspectsConfig.class);

        // 打印spring容器中的 BeanDefinition
        Stream.of(ctx.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        System.out.println("=============================");

        // 正常返回的用例:
        BizRequest bizRequest = ctx.getBean(BizRequest.class);
        Map<String, String> leakParams = new HashMap<>();
        leakParams.put("name", "niewj");

        bizRequest.callData(leakParams);
        ctx.close();
    }
}