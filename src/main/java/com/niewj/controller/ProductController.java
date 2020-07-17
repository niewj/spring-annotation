package com.niewj.controller;

import com.niewj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class ProductController {

//    // 不指定 @Qualifier("productService2") 时, 在 productService2 处使用 @Primary也能首选 productService2
//    // 指定 @Qualifier("productService2")
//    @Qualifier("productService3")  // 如果没有指定 required=false, 匹配不到则会报告异常: NoSuchBeanDefinitionException
//    //  @Autowired(required = false)
//    @Autowired
//    private ProductService productService;

    @Autowired
    private ProductService productService2;

    public void doPrint(){
        System.out.println(productService2);
    }
}
