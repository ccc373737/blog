springboot是spring组件的一站式解决方案，大大简化了spring项目的搭建和开发。

- 与springboot主框架集成，大部分配置都可以自动完成。
- 内置tomcat服务器，jar包直接执行，不用再额外配置。
- 注解优先，摒弃大量的xml配置模式。

####springboot自动装配原理

1.springboot应用入口run方法中，在构造springApplication的构造函数中会去扫描所有
**spring-boot-autoconfigure**以及spring.factories中配置的文件。
后者的作用是注入了一些监听器和initializer初始化类，而前者提供了**所有可能的与springboot集成的组件的xxxAutoconfiguraiotn类**，并放入cache中，
这些xxxAutoconfiguraiotn类就是自动装配的关键。

2.一个关键的解析类configurationClassPostProcessor，这个解析类在springboot启动过程中调用BeanDefinitionRegistryPostProcessor的地方起作用。

这个解析类会处理spring启动类的核心注解@enableAutoConfiguration，这里又会解析到@import(AutoConfigurationImportSelector.class)，正是AutoConfigurationImportSelector
这个类最终执行了自动装配的逻辑：

- 在cache中拿到所有的Autoconfiguraiotn
- 去重和排除逻辑，在主类的注解上可以加上Exclusions排除不想要的自动装配
- 排除pom文件中没有的spring-boot-start-xxx组件
- 解析这些Autoconfiguraiotn类，完成所有bean的注入，而这些bean在传统spring项目中需要手动配置。