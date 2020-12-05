spring中使用事务只需要在方法或类上加上@Transactional注解，事务织入后产生代理类整体的过程与前文AOP产生代理的过程一致。
只不过事务相关的增强器在spring容器启动时就自动加载了，ProxyTransactionManagementConfiguration这个配置类中就定义了事务增强器TransactionInterceptor。

最后依然在createProxy过程中TransactionInterceptor和原始baan生成出一个代理bean。

调用事务的大致流程：

````
伪代码：
//1.根据事务级别创建开启事务
TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);

try {
    //2.业务方法，一个调用链
    retVal = invocation.proceedWithInvocation();
} catch (Throwable ex) {
    //3.出现异常，事务回滚
    completeTransactionAfterThrowing(txInfo, ex);
} finally {
    //4.不管有没有异常，最终都要重置信息
 	cleanupTransactionInfo(txInfo);
}

//5.提交事务
commitTransactionAfterReturning(txInfo);
````

spring事务级别：

- DEFAULT：使用数据库设置的隔离级别 ( 默认 ) ，由 DB默认的设置来决定隔离级别。
- READ_UNCOMMITTED：会出现脏读、不可重复读、幻读 ( 隔离级别最低，并发性能高 )。
- READ_COMMITTED  会出现不可重复读、幻读问题（锁定正在读取的行）。
- REPEATABLE_READ 会出幻读（锁定所读取的所有行）。
- SERIALIZABLE 保证所有的情况不会发生（锁表）。

注：因为mysql的事务级别中RR级别解决了幻读，所以spring事务应用于mysql上，RR级别也就解决了幻读，
如果spring和db的默认事务级别不一致，那么以spring开启的事务级别为准。

spring事务传播：

- required: 支持当前事务，如果不存在 就新建一个(默认)。
- supports: 支持当前事务，如果不存在，就不使用事务。
- mandatory: 支持当前事务，如果不存在，抛出异常。

- requires_new:  如果有事务存在，挂起当前事务，创建一个新的事务。
- not_supported: 以非事务方式运行，如果有事务存在，挂起当前事务。
- never: 以非事务方式运行，如果有事务存在，抛出异常。
- nested: 如果当前事务存在，则嵌套事务执行。