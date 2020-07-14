package com.niewj.condition;

import com.niewj.bean.Pojo4;
import com.niewj.bean.Pojo5;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class MyImportSelector implements ImportSelector {

    /**
     * 返回的数组中的类名, 也会作为加入容器管理的bean
     * @param importingClassMetadata 可以获取到当前类的所有注解信息!
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> classNameList = new ArrayList<>();

        // 可以假设Pojo4/Pojo5的类名都读取字配置文件, 比如 spring.factories-->类似springboot源码
        classNameList.add(Pojo4.class.getName());
        classNameList.add(Pojo5.class.getName());

        return classNameList.toArray(new String[0]);
    }
}
