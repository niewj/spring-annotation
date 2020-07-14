package com.niewj.util;

import com.niewj.controller.BaseController;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义 ComponentScan方式:
 */
public class MyComponentScanTypeFilter implements TypeFilter {
    /**
     * 此处的判断逻辑是:
     * 如果
     * 1. 类是BaseController的子类
     * 2. && 注解包含@Controller, 则匹配!
     *
     * @param metadataReader        获取到的当前类的注解信息
     * @param metadataReaderFactory 可获取到其他类信息的工厂
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 1. 获取当前类的注解信息
        AnnotationMetadata annoMetadata = metadataReader.getAnnotationMetadata();
        // 2. 获取当前正在扫描的类信息:
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        if (isBaseController(classMetadata) && hasControllerAnnotation(annoMetadata)) {
            return true;
        }
        return false;
    }

    /**
     * 条件1: 正在扫描的类 是不是 BaseController的子类
     *
     * @param classMetadata
     * @return
     */
    private boolean isBaseController(ClassMetadata classMetadata) {
        // 3. 正在扫描的类名称
        String className = classMetadata.getClassName();

        // 4. 正在扫描的类的父类, 格式: BookController->com.niewj.controller.BaseController
        String superClassName = classMetadata.getSuperClassName();
        return superClassName.contains(BaseController.class.getSimpleName());
    }

    /**
     * 判断注解中有无Controller "org.springframework.stereotype.Controller"
     *
     * @param annotationMetadata
     * @return
     */
    private boolean hasControllerAnnotation(AnnotationMetadata annotationMetadata) {
        return annotationMetadata.getAnnotationTypes().stream().anyMatch(e -> e.equals("org.springframework.stereotype.Controller"));
    }
}
