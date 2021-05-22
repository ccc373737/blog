mybatis半ORM框架，执行sql返回结果映射到java对象中，与spring集成，很好地支持JDBC。

###1.占位符

{#}占位符，表示单引号

{$}直接替换值，用于groupby，orderby这种不需要引号的，使用$会出现sql注入

###2.缓存和延迟加载

关联查询时才能配置延迟加载，当得到某个字段为null时，才去查sql得到结果

二级 -> 一级 -> 数据库
- 一级缓存，会话级别，默认开启，原理是使用一个hashmap，以sql+namespace等参数作为key，
当sql与map中的key相同时，直接使用缓存中的数据吗。当会话中执行update/insert等操作时，缓存失效。
  
- 二级缓存，mapper级别namespace，默认不开启，相同名字的namespace中的操作都会影响cache，
会出现脏数据。
  
###3.动态代理

jdk动态代理，所以mapper只能是接口，会通过namespace权限名找到mapper生成代理类，本质上是调用mapperProxy.invoke方法。

###4.分页实现

githubPageHelper，在sql后面拼接limit

