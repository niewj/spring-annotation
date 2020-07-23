package com.niewj.util;

import com.niewj.bean.Pojo4;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("===> MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry 调用!!!");
        System.out.println("registry instanceof BeanFactory: " + (registry instanceof BeanFactory));

        System.out.println("###->MyBeanDefinitionRegistryPostProcessor注册bean:[Service2.class]###-> begin");
        // 注册一个bean: Service2(并没有注解 @Component)
        AbstractBeanDefinition myBean = BeanDefinitionBuilder.rootBeanDefinition(Service2.class).getBeanDefinition();
        registry.registerBeanDefinition("service2", myBean);
        System.out.println("###->MyBeanDefinitionRegistryPostProcessor注册bean:[Service2.class]###-> over");

        System.out.println("###->MyBeanDefinitionRegistryPostProcessor注册bean:[Pojo4.class]###-> begin");
        // 注册一个bean: Pojo4
        AbstractBeanDefinition myPojo4 = BeanDefinitionBuilder.rootBeanDefinition(Pojo4.class).getBeanDefinition();
        registry.registerBeanDefinition("myPojo4", myPojo4);
        System.out.println("###->MyBeanDefinitionRegistryPostProcessor注册bean:[Pojo4.class]###-> over");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 1. 可以再次修改容器内一些bean的特征: 如设置 Pojo4类延迟初始化!
        BeanDefinition myPojo4 = beanFactory.getBeanDefinition("myPojo4");
        myPojo4.setLazyInit(true);

        // 2. 可以获取容器内bean定义的数量
        System.out.println("===> MyBeanDefinitionRegistryPostProcessor#postProcessBeanFactory-->可以获取容器内bean定义的数量->getBeanDefinitionCount=> " + beanFactory.getBeanDefinitionCount());

        // 3. 可以打印容器中的所有 定义的BeanDefinition
        System.out.println("===> MyBeanDefinitionRegistryPostProcessor#postProcessBeanFactory-->可以打印容器中的所有 定义的BeanDefinition->getBeanDefinitionNames:\t");
        System.out.println(Arrays.asList(beanFactory.getBeanDefinitionNames()));
    }
}
