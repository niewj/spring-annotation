package com.niewj.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("===> MyBeanFactoryPostProcessor#postProcessBeanFactory-->可以获取容器内bean定义的数量->getBeanDefinitionCount=> " + beanFactory.getBeanDefinitionCount());
        System.out.println("===> MyBeanFactoryPostProcessor#postProcessBeanFactory-->可以打印容器中的所有 定义的BeanDefinition->getBeanDefinitionNames:");
        System.out.println(Arrays.asList(beanFactory.getBeanDefinitionNames()));
    }
}
