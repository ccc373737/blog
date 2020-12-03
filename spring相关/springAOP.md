###AOP概念

AOP（Aspect-OrientedProgramming，面向方面编程），可以说是OOP（Object-Oriented Programing，面向对象编程）的补充和完善。OOP引入封装、继承和多态性等概念来建立一种对象层次结构，用以模拟公共行为的一个集合。
当我们需要为分散的对象引入公共行为的时候，OOP则显得无能为力。
也就是说，OOP允许你定义从上到下的关系，但并不适合定义从左到右的关系。
例如日志功能。日志代码往往水平地散布在所有对象层次中，而与它所散布到的对象的核心功能毫无关系。对于其他类型的代码，如安全性、异常处理和透明的持续性也是如此。这种散布在各处的无关的代码被称为横切（cross-cutting）代码，在OOP设计中，它导致了大量代码的重复，而不利于各个模块的重用。

而AOP技术则是面向切面，AOP 专门用于处理系统中分布于各个模块（不同方法）中的交叉关注点的问题，并将那些影响了多个类的公共行为封装到一个可重用模块，横向织入到业务代码中。
便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可操作性和可维护性。

AspectJ就是一个面向切面的框架，它扩展了java语法。AspectJ定义了AOP语法，并有一个专门的编译器来生成遵守java字节码规范的class文件。
AspectJ属于静态代理，编译时增强，所以 AspectJ才需要使用自己的编译器来编译Java文件。

而后来的springAOP借鉴了AspectJ的切面思想和语法，但它使用的是动态代理技术，运行时生成代理，并不依赖AspectJ的编译器。

###Spring AOP

springAOP有以下几种通知，分别会织入到业务代码的不同位置：

- 前置通知(@Before)：在目标方法运行之前运行
- 后置通知(@After)：logEnd：在目标方法运行结束之后运行（无论方法正常结束还是异常结束）
- 返回通知(@AfterReturning)：在目标方法(div)正常返回之后运行
- 异常通知(@AfterThrowing)：在目标方法(div)出现异常以后运行
- 环绕通知(@Around)：动态代理，手动推进目标方法运行

这里的每一个实际通知方法又称为增强器。

spring生成代理类的过程分为三步

1.获取增强器

首先spring中的原始bean -> 代理bean这一过程都是在初始化后处理中运行。

方法调用链为initializeBean -> applyBeanPostProcessorsAfterInitialization -> AbstractAutoProxyCreator.postProcessAfterInitialization -> wrapIfNecessary

AbstractAutoProxyCreator这个后置处理器专门用于bean在初始化过程中将其转为代理bean。

wrapIfNecessary方法中首先会调用getAdvicesAndAdvisorsForBean获取增强器：

- 获取便遍历所有bean，找出@AspectJ或xml配置代理的对象。
- 解析这个类中切点执行方法，及切点位置。
- 将解析提取到的增强器放入一个缓存中。

这一步之后，整个应用中所有的增强器如事务增强器BeanFactoryTransactionAttributeSourceAdvisor，自定义增强器等都全部放一个缓存中了。

2.根据当前bean得到对应的增强器

findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);

这个方法简单来说就是根据增强器的属性判断当前类是否需要增强，最后选出当前bean适用的增强器。

3.创建代理对象

createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean)) ->

proxyFactory.getProxy(getProxyClassLoader()); ->

createAopProxy().getProxy(classLoader);

这里创建的原理本质上就是java动态代理的原理，首先判断类如果实现了接口，那么使用jdk动态代理生成，如果没有，那么使用cglib生成。

最终在invoke()方法中实现AOP增强的逻辑。

而代理对象的实际执行采用一种执行链的模式，如一个前后都有增强的方法，实际上是先进入afterPoint逻辑，伪代码如下：
````
public void afterPoint() {
    beforePoint();

    //代码的最后执行后置逻辑
    afterMethod();
}

public void before() {
    //先执行前置逻辑
    beforeMethod;

    //真正的业务代码处
    realMethod();
}
````
上述的伪代码只调用了三层执行，实际的应用中，这个调用链可能非常长，每个增强方法，只处理自己相关的增强逻辑，然后交给执行链的下一个元素，
而不是在一个invoke方法按顺序执行增强方法和实际方法。

如果使用invoke方法，那么就需要在一开始就知道所有的增强器以及执行顺序，使用调用链的模式实现了顺序上解耦。
					

