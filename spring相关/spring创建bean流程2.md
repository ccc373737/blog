###5单例bean的实例化

finishBeanFactoryInitialization(beanFactory);

先介绍几个重要缓存：

- beanDefinitionMap：beanName -> beanDefinition
- mergedBeanDefinitions：beanName -> 合并之后的bean定义
- earlySingletonObject：beanName -> 实例化但未填充属性的bean对象
- singletonObjects：beanName -> 单例bean对象
- singletonFactories：beanName -> ObjectFactory

1 在bean的实例化之前，spring已经完成了所有bean的定义的解析，并且这些bean都以(beanName,BeanDefinition)的形式放入了map容器中。
然后这个方法会遍历所有BeanDefinition，首先进行bean的合并操作
    
    - 如果当前bean存在父定义，使用父定义构建RootBeanDefinition。
    - 如果不存在父定义，且本身是RootBeanDefinition，直接返回。
    - 如果不存在父定义，但本身不是RootBeanDefinition，那么构建一个RootBeanDefinition返回。
总之这一步就是将注册的bean信息转化为RootBeanDefinition，这些对象会放入mergedBeanDefinitions缓存中，供后面实例化时使用。


2 遍历map开始生成bean，判断当前bean是否是factorybean，这个bean是spring中一个特殊的bean，实现了这个接口就不走spring生成bean的逻辑，而是自定义getObject()方法返回获得bean

3 getbean(beanName)，正式进入实例化bean的逻辑，首先从前面的缓存map中获得RootBeanDefinition对象，并检查是否有依赖（如@dependon注解），如果有，那么先递归创建这些依赖bean。然后根据不同的scope创建bean，下面以单例为例，进入create方法。

3.1 create(bean)，这个方法里首先会创建一个副本bean备份以及标记override方法，然后进入实例化前置处理，
spring会遍历所有注册的processor，从中找到一类InstantiationAwareBeanPostProcessor处理器并执行，实现了这个处理器的processor，有两个方法：
    
    - postProcessBeforeInstantiation
    - postProcessAfterInstantiation
    
前者是RootBeanDefinition转化为BeanWrapper前的最后一次修改机会，**AOP代理功能**就在在这一步将bean变为一个代理对象。

后者则是在上一步如果成功转化bean的基础上，进一步对bean进行操作，注：如果成功这里转化了对象，那么不再执行后面的生成逻辑，即认为bean对象已经生成，直接返回。

如果对象没有被processor转化，即认为是一个常规bean，那么进入doCreateBean流程。

3.3  doCreateBean(beanName, mbdToUse, args)，这个方法第一步是根据RootBeanDefinition创建BeanWrapper实例（BeanWrapper是实际类的包装类，这里实际可以理解为bean的实例化），整个实例化过程非常复杂，简单总结实例化的流程

- 根据参数类型或指定配置确定构造方法。
- 根据传入参数确定构造方法的参数。
- 根据反射或代理生成对象，后者应用于在使用了如look-up等配置的情况下。

注：90%的bean都是使用无参构造生成的。

3.4 applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)，这里又是一类MergedBeanDefinitionPostProcessor解析器的使用，
这个解析器会修改rootbean，@autowired，@resource这类的属性注册就是在这里完成的，
注：这里只是完成解析，注入的对象在后面几步中实例化。

3.5 为解决循环依赖，将这个已经实例化但未初始化的bean提前曝光，放入一个map中。

spring解决循环依赖使用三级缓存方法，注意通过构造器方法注入依赖的循环依赖无法通过此方法解决，因为这个方法本质上将实例化对象和注入属性分成两步完成，
使用构造器则是这两步是在一起实现的。且只有针对单例对象才成立，因为peototype并不缓存对象。

具体实现如下：spring在获取属性对象时，先从singletonObjects（一级缓存）中获取，如果无法获取，从earlySingletonObject（二级缓存中获取），
如果再无法获取，从singletonFactories（三级缓存中获得）。

如A完成实例化，并将自己提取曝光到singletonFactories中，随后注入属性，发现A依赖B，进行B的初始化，
B初始化过程中发现也依赖于A，首先尝试去缓存中获取A，在singletonFactories发现了A，顺序拿到A的引用，完成了自己的初始化。
然后A也拿到了B的引用，因为单例的缘故，A，B的引用不会变动，这样循环依赖就解决了。

注：本质上这个过程只需要一个缓存就可以实现，之所以多级可能是为用户提供一些接口扩展空间。

3.6 populateBean(beanName, mbd, instanceWrapper)，bean对象进行属性填充，即@autowired这类对象在这里进行实例化，这里又有可能存在多级依赖，那么递归实例化。

- 再一次调用一类InstantiationAwareBeanPostProcessor解析器，这个解析器的InstantiationAwareBeanPostProcessor方法决定了bean是否要进行下一步的属性填充。

3.7 initializeBean(beanName, exposedObject, mbd)，初始化bean，这个方法一共四个步骤，比较清晰。

- 激活aware方法，如果bean实现了xxxaware接口，那么spring将一些数据放入bean中，如applicationContext上下文信息，beanName信息等。
- applyBeanPostProcessorsBeforeInitialization，找到所有processor，执行初始化前置方法postProcessBeforeInitialization。
- 执行init方法，如果是通过注解实现，那么执行@postconstrut方法。
- applyBeanPostProcessorsAfterInitialization，找到所有processor，执行初始化后置方法postProcessAfterInitialization。

至此，一个bean就创建完毕了，这个bean会被放入singletonObjects单例对象缓存中，由singletonObjects传递到外部函数，
如：web请求中，dispatchservlet可以从url映射controller beanName，然后从这个缓存中拿到xxxController对象。