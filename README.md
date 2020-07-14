# spring-annotation
Spring注解驱动

## 1. ComponentScan注解

```
package com.niewj.config;

import com.niewj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan("com.niewj")
public class MainConfig {

    @Bean
    public Person person() {
        return new Person("張三", 22);
    }
}
```

### (1). 常规: 

	`@ComponentScan(value="com.niewj")`

### (2). `FilterType.ANNOTATION` 只排除注解:

	`value`  + `excludeFilters`

```java
@ComponentScan( // 只排除:
        value = "com.niewj",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}
)
```
### (3).`FilterType.ANNOTATION ` 只过滤注解:
	需要加上 `value` + `useDefaultFilters=false` + `includeFilters`

```java
@ComponentScan( // 只包含: 必须要写 useDefaultFilters 否则不生效
        value = "com.niewj",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}
)
```
### (4). `FilterType.ASSIGNABLE_TYPE`指定具体的类型:
	includeFilters 和 excludeFilters 本身就是个数组!

```
@ComponentScan( // 只包含: 必须要写 useDefaultFilters 否则不生效
        value = "com.niewj",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookService.class)
        }
)
```

#### (4). 自定义 TypeFilter @ComponentScan 用法:

```
@Test
public void testTypeFilter() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
}
```

输出如下: 

```shell
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
bookController
person
```

MainConfig.java:

```
package com.niewj.config;

import com.niewj.bean.Person;
import com.niewj.util.MyComponentScanTypeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
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
```

主要看 MyComponentScanTypeFilter的类声明: 它的判断逻辑是满足一下两点, 则匹配!

> 1. 如果类有注解@Controller 
> 2. 如果类是BaseController的子类

```
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
```

###  (5). 小结:
ComponentScan扫描大致常用有这么几类: 

1.普通@ComponentScan(value="com.niewj")

2.注解过滤排除: @ComponentScan(value="com.niewj", excludeFilters={filter1, filter2,...})

3.注解过滤包含: @ComponentScan(value="com.niewj", includeFilters={filter1, filter2, ...})

其中的 Filter 包含 

(1). 注解过滤匹配: type = FilterType.ANNOTATION

(2). 指定的类名比配: type=FilterType.ASSIGNABLE_TYPE

(3).自定义类型匹配: type=FilterType.CUSTOM  如示例中的: MyComponentScanTypeFilter implements `TypeFilter`

(4). type=FilterType.ASPECTJ (不常用)

(5). 正则匹配 type=FilterType.REGEX (不常用)

备注: com.niewj也可以扫描到子包哦!



## 2. Conditional注解

此注解用于配合 `@Bean` 注解, 限定Bean注解的条件: 如果满足条件, 响应的Bean才会放入容器; 否则不会!

springboot大量使用此注解!

> Conditional注解可以在满足某条件时才初始化Bean, 条件就是实现了Condition接口的match方法的逻辑!本实例就是根据VM option运行时传入一个参数 -Dspring.profiles.active=xxx, 根据xxx是dev还是product来决定生成的对象User是 admin还是user01



 ### (1). VM options增加参数

`-Dspring.profiles.active=product`

![image-20200713180310059](C:\Users\weiju\AppData\Roaming\Typora\typora-user-images\image-20200713180310059.png)

### (2). 测试代码:

```
@Test
public void testConditinalBean() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ConditionalConfig.class);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);

    Environment environment = ctx.getEnvironment();
    String profile = environment.getProperty("spring.profiles.active");
    System.out.println(profile);

    User user = ctx.getBean(User.class);
    System.out.println(user);
}
```

### (3). 输出: 

>User-初始化!
>conditionalConfig
>uProduct
>product
>User{name='user01', passwd='realP@sswd', online=true}



### (4). ConditionalConfig.java: 

可以看到: ConditionalConfig中, 使用了注解: `@Conditional({ConditionalDev.class})`  `@Conditional({ConditionalProduct.class})` 两个@Bean, 但是实际上初始化和输出的只有 product, 是-D参数的选择!!

```
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
```

### (5). User.java实体类:

```
package com.niewj.bean;

import lombok.Data;

@Data
public class User {
    private String name;
    private String passwd;
    private boolean online ;

    public User(String name, String passwd, boolean online){
        System.out.println("User-初始化!");
        this.name = name;
        this.passwd = passwd;
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", online=" + online +
                '}';
    }
}
```

### (6). 核心: Condition接口实现
ConditionalDev + ConditionalProduct

```
package com.niewj.condition;

import com.niewj.ConstUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * spring.profiles.active=dev 时满足条件
 */
public class ConditionalDev implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String profile = context.getEnvironment().getProperty(ConstUtil.PROFILES_ACTIVE);
        if (ConstUtil.DEV.equalsIgnoreCase(profile)) {
            return true;
        }
        return false;
    }
}
```

ConditionalDev在条件: -Dspring.profiles.active=dev时返回true!

ConditionalProduct在条件: -Dspring.profiles.active=product时返回true!

可见, 可以根据某个传入参数来选择是否初始化某个Bean, 比如: 用来区分生产和dev环境~~

```
package com.niewj.condition;

import com.niewj.ConstUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * profile=product 时满足条件
 */
public class ConditionalProduct implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String profile = context.getEnvironment().getProperty(ConstUtil.PROFILES_ACTIVE);
        if (ConstUtil.PRODUCT.equalsIgnoreCase(profile)) {
            return true;
        }
        return false;
    }
}
```

### (7). 小结:

1. @Conditional 注解可以配合@Bean注解, 在Springboot中大量应用!
2. @Conditional注解可以用于 方法 也可以用于类, 用在类上表示: 所有方法限定!!
3. 需要标注具体的Condition实现类: 具体逻辑实现在 match方法里! 不同的条件实现不同的Condition子类!



## 3. 向Spring容器中注册组件的方法

### 1. 包扫描+组件标注注解 

@ComponentScan+ @Controller + @Service + @Repository

### 2. @Bean注解导入

 导入第三方包里的组件

### 3. @Import快速导入

几个实体类的声明:
```
package com.niewj.bean;

public class PojoBean {
    public PojoBean() {
        System.out.println("PojoBean[无注解java类] 初始化~~~");
    }
}
```

```
package com.niewj.bean;

public class PojoBean2 {
    public PojoBean2(){
        System.out.println("PojoBean2[无注解java类] 初始化~~~");
    }
}
```

```
package com.niewj.bean;

public class PojoBean3 {
    public PojoBean3(){
        System.out.println("PojoBean3[无注解java类] 初始化~~~");
    }
}
```

```
package com.niewj.bean;

public class Pojo4 {
    public Pojo4(){
        System.out.println("Pojo4[无注解java类] 初始化~~~");
    }
}
```

```
package com.niewj.bean;

public class Pojo5 {
    public Pojo5(){
        System.out.println("Pojo5[无注解java类] 初始化~~~");
    }
}
```



#### (1) @Import导入单个

```java
package com.niewj.config;

import com.niewj.bean.PojoBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PojoBean.class) // 这里
public class ImportTestConfig {
}
```

```java
private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
    AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(clazz);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    return ctx;
}

@Test
public void testImport(){
    // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
    ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
}
```

output: 

```shell
PojoBean[无注解java类] 初始化~~~
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
importTestConfig
com.niewj.bean.PojoBean
```

可以看到 PojoBean 的初始化和bean id的展示!

#### (2) @Import导入多个(数组)

普通Javabean并没有任何注解的: 

```java
package com.niewj.config;

import com.niewj.bean.PojoBean;
import com.niewj.bean.PojoBean2;
import com.niewj.bean.PojoBean3;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(PojoBean.class)
@Import({PojoBean.class, PojoBean2.class, PojoBean3.class}) // 这里
public class ImportTestConfig {
}
```

测试:

```
private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
    AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(clazz);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    return ctx;
}

@Test
public void testImport(){
    // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
    ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
}
```



output: 可以看到这三个无注解的普通bean也被容器管理和初始化了!

```sheell
PojoBean[无注解java类] 初始化~~~
PojoBean2[无注解java类] 初始化~~~
PojoBean3[无注解java类] 初始化~~~
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
importTestConfig
com.niewj.bean.PojoBean
com.niewj.bean.PojoBean2
com.niewj.bean.PojoBean3
```

#### (3) @ImportSelector接口

@ImportSelector接口的实现类, 可以放入@Import中使用:

@Import是列出所有; @ImportSelector是返回一个 class name的字符串的数组, 可以理解为@Import的简化版(方便使用)

```
package com.niewj.config;

import com.niewj.bean.PojoBean;
import com.niewj.bean.PojoBean2;
import com.niewj.bean.PojoBean3;
import com.niewj.condition.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(PojoBean.class)
//@Import({PojoBean.class, PojoBean2.class, PojoBean3.class})
@Import({PojoBean.class, PojoBean2.class, PojoBean3.class, MyImportSelector.class}) // 这里
public class ImportTestConfig {
}
```

MyImportSelector: 

```
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
```

testcase:

```
private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
    AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(clazz);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    return ctx;
}

@Test
public void testImport(){
    // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
    ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
}
```

output:  可见: PojoBean/PojoBean2/PojoBean3都初始化了; 而且 ImportSelector中加入数组的 Pojo4/Pojo5也初始化了!

```shell
PojoBean[无注解java类] 初始化~~~
PojoBean2[无注解java类] 初始化~~~
PojoBean3[无注解java类] 初始化~~~
Pojo4[无注解java类] 初始化~~~
Pojo5[无注解java类] 初始化~~~
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
importTestConfig
com.niewj.bean.PojoBean
com.niewj.bean.PojoBean2
com.niewj.bean.PojoBean3
com.niewj.bean.Pojo4
com.niewj.bean.Pojo5
```



#### (4). ImportBeanDefinitionRegistrar

`可以结合已有beanDefinition信息!`

```
package com.niewj.config;

import com.niewj.bean.PojoBean;
import com.niewj.bean.PojoBean2;
import com.niewj.bean.PojoBean3;
import com.niewj.condition.MyImportBeanDefRegistrar;
import com.niewj.condition.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import(PojoBean.class)
//@Import({PojoBean.class, PojoBean2.class, PojoBean3.class})
@Import({PojoBean.class, PojoBean2.class, PojoBean3.class, MyImportSelector.class, MyImportBeanDefRegistrar.class})
public class ImportTestConfig {
}
```

MyImportBeanDefRegistrar: 

```
package com.niewj.condition;

import com.niewj.bean.Pojo4;
import com.niewj.bean.Pojo5;
import com.niewj.bean.PojoWhenHasPojo4AndPojo5;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefRegistrar implements ImportBeanDefinitionRegistrar {

    /** 可以获取容器中已有 BeanDefinition 信息来做一些判断; 
     * 比如, 如果有这两个类, 也加入类 PojoWhenHasPojo4AndPojo5
     * @param importingClassMetadata 可以获取到当前类(扫描到的类)的所有注解信息
     * @param registry beanDefinition的注册接口
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean hasPojo5 = registry.containsBeanDefinition(Pojo5.class.getName());
        boolean hasPojo4 = registry.containsBeanDefinition(Pojo4.class.getName());
        
        if(hasPojo4 && hasPojo5){
            RootBeanDefinition beanDefinition = new RootBeanDefinition(PojoWhenHasPojo4AndPojo5.class);
            registry.registerBeanDefinition("pojoWhenHasPojo4AndPojo5", beanDefinition);
        }
    }
}
```

测试:

```
private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
    AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(clazz);
    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    return ctx;
}

@Test
public void testImport(){
    // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
    ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
}
```

output: 可以看到 PojoWhenHasPojo4AndPojo5 的初始化!

```shell
PojoBean[无注解java类] 初始化~~~
PojoBean2[无注解java类] 初始化~~~
PojoBean3[无注解java类] 初始化~~~
Pojo4[无注解java类] 初始化~~~
Pojo5[无注解java类] 初始化~~~
PojoWhenHasPojo4AndPojo5[无注解java类] 初始化~~~
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
importTestConfig
com.niewj.bean.PojoBean
com.niewj.bean.PojoBean2
com.niewj.bean.PojoBean3
com.niewj.bean.Pojo4
com.niewj.bean.Pojo5
pojoWhenHasPojo4AndPojo5
```



### 4. Spring的FactoryBean\<T\>接口(工厂Bean)

- getObject()  获取要生产的对象

- getObjectType() 获取要生产的对象的类型

- isSingleton() 定义是单例还是多例

  注意: 通过 ctx.getBean("beanId") 调用, 得到的是 getObject()方法返回的对象的实际内容;

  通过ctx.getBean("&beanId")调用, 得到的才是实际的FactoryBean对象

```
package com.niewj.bean;

public class Pojo7 {
    public Pojo7(){
        System.out.println("Pojo7[无注解java类] 初始化~~~");
    }
}
```

```
package com.niewj.bean;

import org.springframework.beans.factory.FactoryBean;

public class Pojo7FactoryBean implements FactoryBean<Pojo7> {
    @Override
    public Pojo7 getObject() throws Exception {
        System.out.println("Pojo7FactoryBean---FactoryBean---初始化");
        return new Pojo7();
    }

    @Override
    public Class<?> getObjectType() {
        return Pojo7.class;
    }
    
    @Override
    public boolean isSingleton() {
        return false;
    }
}
```


```
    private AnnotationConfigApplicationContext printAnnoBeans(Class clazz) {
        AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(clazz);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        return ctx;
    }

    @Test
    public void testImport(){
        // PojoBean*.java并无注解类, 但是会初始化归入spring容器管理! -> @Import的作用!
        ApplicationContext ctx = printAnnoBeans(ImportTestConfig.class);
        Pojo7 bean1 = ctx.getBean("pojo7", Pojo7.class);
        System.out.println(bean1);
        Pojo7FactoryBean bean2 = ctx.getBean("&pojo7", Pojo7FactoryBean.class);
        System.out.println(bean2);
    }
```

output: 看最后两行: 说明了 FactoryBean的特征: &beanId得到是 FactoryBean; beanId得到是 getObject()返回的实际Bean!

```shell
PojoBean[无注解java类] 初始化~~~
PojoBean2[无注解java类] 初始化~~~
PojoBean3[无注解java类] 初始化~~~
Pojo4[无注解java类] 初始化~~~
Pojo5[无注解java类] 初始化~~~
PojoWhenHasPojo4AndPojo5[无注解java类] 初始化~~~
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
importTestConfig
com.niewj.bean.PojoBean
com.niewj.bean.PojoBean2
com.niewj.bean.PojoBean3
com.niewj.bean.Pojo4
com.niewj.bean.Pojo5
pojo7
pojoWhenHasPojo4AndPojo5
Pojo7FactoryBean---FactoryBean---初始化
Pojo7[无注解java类] 初始化~~~
com.niewj.bean.Pojo7@2bbf180e
com.niewj.bean.Pojo7FactoryBean@163e4e87
```

### 5. 小结

向spring容器中注册组件有四种方式:
1. @ComponentScan+注解 @Controller/@Service/@Response;
2. @Bean注解导入;
3. @Import注解导入普通bean;
4. 实现spring的FactoryBean<T>接口, 通过@Bean加入容器;

@Import导入普通bean也有四种方式:
1. @Import(单个bean)
2. @Import({多个bean-1, 多个bean-2, ...}
3. @Import({bean1.class, bean2.class, ImportSelector.class})
4. @Import({bean1.class, bean2.class, ImportSelector.class, ImportBeanDefinitionRegistrar.class})



## 4. Spring Bean的生命周期

### (1). Bean的创建

bean的创建实际上就是指 构造方法的调用;

`单例Bean` 容器初始化时会预先调用, 除非标注了 @Lazy 注解指定懒加载(延迟创建);

`prototype(原型)bean` 当第一次调用 getBean方法时, 才会调用构造方法

### (2). Bean的初始化和销毁

bean的初始化, 指的是在构造方法调用之后, 对象的一些初始化操作;

bean的销毁, 指的是在spring容器关闭前, 对bean对象做的一些后续处理操作的调用;

Bean的初始化和销毁有一下几种方法:

#### (2.1). @Bean注解指定;

#### (2.2). InitializingBean/DisposableBean 接口;

#### (2.3). @PostConstruct/@PreDestroy 注解(JSR250);

#### (2.4). BeanPostProcessor接口(作用范围广);

