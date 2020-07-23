package com.niewj.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 通过 BeanPostProcessor 接口
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        System.out.println("MyBeanPostProcessor-初始化!");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 可以通过此方法, 找到某一个目标 bean, 做一些操作: 如给容器中的 Service1 依赖的空对象赋值
        // 注意: 这里的 Service1.setService2(new Service2()) 中的 Service2 不会注册到容器中!
        // 但是 在Service1中使用 Service2的方法时, 是有我们赋值的对象的;
        System.out.println("###->MyBeanPostProcessor#postProcessBeforeInitialization->beanName=[" + beanName + "]###-> begin");
        if (bean instanceof Service1) {
            Service1 service1 = (Service1) bean;
            System.out.println("bean=" + bean + "; bean依赖为: " + service1.getService2());
            // 注入依赖(自己new的)
            service1.setService2(new Service2());
        }
        System.out.println("###->MyBeanPostProcessor#postProcessBeforeInitialization->beanName=[" + beanName + "]###-> end");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 由于上面已经赋值, 这里可以拿到
        System.out.println("###->MyBeanPostProcessor#postProcessAfterInitialization->beanName=[" + beanName + "]###-> begin");
        if (bean instanceof Service1) {
            Service1 service1 = (Service1) bean;
            System.out.println("bean=" + bean + "; bean依赖为: " + service1.getService2());
        }
        System.out.println("###->MyBeanPostProcessor#postProcessAfterInitialization->beanName=[" + beanName + "]###-> end");
        return bean;
    }
}