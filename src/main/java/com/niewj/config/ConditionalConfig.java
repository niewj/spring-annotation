package com.niewj.config;

import com.niewj.bean.User;
import com.niewj.condition.ConditionalDev;
import com.niewj.condition.ConditionalProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {

    @Conditional({ConditionalDev.class})
    @Bean("uDev")
    public User userDev(){
        return new User("admin", "admin", false);
    }

    @Conditional({ConditionalProduct.class})
    @Bean("uProduct")
    public User userProduct(){
        return new User("user01", "realP@sswd", true);
    }
}
