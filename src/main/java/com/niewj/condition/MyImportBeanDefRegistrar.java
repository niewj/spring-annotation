package com.niewj.condition;

import com.niewj.bean.Pojo4;
import com.niewj.bean.Pojo5;
import com.niewj.bean.PojoWhenHasPojo4AndPojo5;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param importingClassMetadata 可以获取到当前类(扫描到的类)的所有注解信息
     * @param registry beanDefinition的注册接口
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean hasPojo5 = registry.containsBeanDefinition(Pojo5.class.getName());
        boolean hasPojo4 = registry.containsBeanDefinition(Pojo4.class.getName());
        // 可以获取容器中已有的 BeanDefinition 信息 来做一些判断; 比如, 如果有这两个类, 也加入类 PojoWhenHasPojo4AndPojo5
        if(hasPojo4 && hasPojo5){
            RootBeanDefinition beanDefinition = new RootBeanDefinition(PojoWhenHasPojo4AndPojo5.class);
            registry.registerBeanDefinition("pojoWhenHasPojo4AndPojo5", beanDefinition);
        }
    }
}
