spring容器启动创建bean的大致流程：

无论是传统的spring启动方式，还是springboot启动，或者是显式通过ClassPathXmlApplicationContext创建，
最终创建bean的逻辑都会进入到ConfigurableApplicationContext.refresh()中
````
public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
````

这个核心方法可以概况分为以下几步：

1.容器刷新前置操作，系统属性及环境变量的初始化。

2.创建beanFactory，默认实现为DefaultListableBeanFactory，并将bean对象封装为BeanDefinition，将beanName和BeanDefinition放入一个map中。

3.beanFactory后处理，触发BeanFactoryPostProcessor，这个接口是BeanFactory对外暴露的扩展点，可以在实例化bean之前读取bean的定义，进行修改或添加bean的操作。

4.注册BeanFactoryPorcessor，这个接口是初始化bean对外的暴露点，可以添加一些初始化bean前后需要执行的逻辑。
注意，这里只注册，真正生效是在初始化之时。

5.完成所有非懒加载单例bean的实例化，这里是真正创建对象的地方，有一套异常复杂的逻辑，后面会解释。

6.完成所有上下文刷新，一个容器就完整地生成了。

****
###2创建beanFactory和BeanDefinition详解

2.1 创建新的beanFactory实现容器DefaultListableBeanFactory。

2.2 获取配置文件并读取，通常指一些xml文件，将配置文件解析之后传入factory中。

2.3 解析xml文件中的所有标签，这里又分为默认标签和自定义标签，默认标签有import，alias，bean，beans等，默认子标签有constr-args，value等，
有一套对应标签解析的复杂逻辑，最后根据标签得到完整的bean定义BeanDefinition对象，并注册入map中。
而自定义标签则是用户自己定义一个XSD描述文件，根据描述文件解析xml，后续操作与默认标签相似。

经过这一步之后，所有通过配置文件定义的bean对象就都已经完成了注册。

2.4 prepareBeanFactory(beanFactory)，向beanFactory中加入一些工具bean和环境bean，如表达式EL语言处理器等。
并设置几个忽略自动装配的特殊接口，如xxxAware接口，这时spring服务对外暴露的地方，实现了aware接口的子类可以获得spring容器的相关属性，如
````
public class MyBeanNameAware implements BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("reste1111" + name);
    }
}
````
实现了BeanNameAware的接口，在spring启动过程中，会调用所有BeanNameAware接口的setBeanName方法，
此时可以获得beanName做相关业务操作。

****
###3beanFactory后处理详解

invokeBeanFactoryPostProcessors(beanFactory);

BeanFactoryPostProcessor可以在容器实例化任何其他bean之前读取配置元数据（即BeanDefinition），并修改它，
注解相关对应的bean注册就是在此实现。

BeanFactoryPostProcessor有一类特殊的实现接口BeanDefinitionRegistryPostProcessor，在后处理的实现过程中，
先执行BeanDefinitionRegistryPostProcessor接口的逻辑，在执行BeanFactoryPostProcessor的逻辑，一般而言，前者更侧重注册信息，比如它甚至可以注册常规的BeanFactoryPostProcessor，
后者侧重于修改BeanDefinition对象的信息，也可以直接注册bean对象。

注：在springboot中createContext中会注入一个用于解析注解的关键BeanFactoryPostProcessor，
map.put(internalConfigurationAnntationProcessor, configurationClassPostProcessor)

3.1 入参级别的BeanDefinitionRegistryPostProcessor调用postProcessBeanDefinitionRegistry方法，
入参级别是指，在spring前置加载中就被放入容器processor，这部分processor在xxxInitializer配置文件中被定义。

3.2 已经存在于map中的实现了Registry和PriorityOrdered的接口被调用，configurationClassPostProcessor就是再这里开始解析注解。

3.3 已经存在于map中的实现了Registry和Ordered的接口被调用。

3.4 剩余实现了Registry的接口被调用，至此所有Registry接口调用完毕，再这个过程中可能不断有BeanFactoryPostProcessor接口对象被加入map。

3.5 入参级别实现了BeanFactoryPostProcessor接口调用。

3.6 已经存在于map中的实现了BeanFactoryPostProcessor和PriorityOrdered的接口被调用。

3.7 已经存在于map中的实现了BeanFactoryPostProcessor和Ordered的接口被调用。

3.8 剩余实现了BeanFactoryPostProcessor的接口被调用。

通过上述的分析，可以看到对于BeanFactoryPostProcessor接口的调用形成一种有优先级关系的前后顺序，这个过程是springboot时代中实际注入大量bean（注解注入）以及一些中间件放入扩展信息的位置。
****
####configurationClassPostProcessor注解解析器

这个解析器会扫描项目全包，找到所有加上@Configuration和@Component（注：所有@service，@controller本质上都是@Component）的类放入spring容器中。

然后会继续引入加上@import，@importSelector，@importBeanDefinitionRegistry的注解的bean，以及上一步@Configuration中通过@bean的方法生成的bean加入
容器。

****
###4BeanPostProcessor

之前的BeanFactoryPostProcessor是针对的BeanFactory的扩展，在实例化之前读取修改增加bean，
而BeanPostProcessor是针对bean的扩展，用于在bean初始化前后对bean进行进一步处理。

这里整体的流程与BeanFactoryPostProcessor类似，以一种优先级关系前后注册，**但是这里只注册，不调用**，
具体的调用是在bean初始化前后调用注册的postProcessBeforeInitialization方法和postProcessAfterInitialization。

