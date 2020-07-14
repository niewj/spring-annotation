package com.niewj.config;

import com.niewj.bean.Person;
import com.niewj.util.MyComponentScanTypeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@ComponentScan("com.niewj")

//@ComponentScan( // 只排除:
//        value = "com.niewj",
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}
//)

//@ComponentScan( // 只包含: 必须要写 useDefaultFilters 否则不生效
//        value = "com.niewj",
//        useDefaultFilters = false,
//        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}
//)

//@ComponentScan( // 只包含: 必须要写 useDefaultFilters 否则不生效
//        value = "com.niewj",
//        useDefaultFilters = false,
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookService.class)
//        }
//)

@ComponentScan(value = "com.niewj",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyComponentScanTypeFilter.class)
        }
)
public class MainConfig {

    @Bean
    public Person person() {
        return new Person("張三", 22);
    }
}
