package com.niewj.config;

import com.niewj.bean.Pojo7FactoryBean;
import com.niewj.bean.PojoBean;
import com.niewj.bean.PojoBean2;
import com.niewj.bean.PojoBean3;
import com.niewj.condition.MyImportBeanDefRegistrar;
import com.niewj.condition.MyImportSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(PojoBean.class)
//@Import({PojoBean.class, PojoBean2.class, PojoBean3.class})
@Import({PojoBean.class, PojoBean2.class, PojoBean3.class, MyImportSelector.class, MyImportBeanDefRegistrar.class})
public class ImportTestConfig {

    @Bean("pojo7")
    public Pojo7FactoryBean pojo7(){
        return new Pojo7FactoryBean();
    }
}
