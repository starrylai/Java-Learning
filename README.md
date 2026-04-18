This is a repository for Java learning.  

# Java-Learning  
## 1. Student System  
*登陆注册 增删查改*  
基础语法  
  
## 2. OOP Exercise  
*面向对象编程练习*
### （1） BankAccount  
银行账户系统建模 活期定期账户存取款与利息计算  
多态 重写 抽象类  
### （2） AreaCalculator
面积计算器  
接口多态  
### （3） UserDeduplication  
`HashSet`与`TreeSet`去重  
重写`equals`与`hashCode`（`HashSet`）  
重写`compareTo`（`TreeSet`）
### （4） OrderStatus
订单状态转移  
枚举`enum` `final`  
  
## 3. IO and Exception
*核心类库 IO Exception*
### （1） WordFrequency
文件中单词频率统计  
`IOException` `Comparator` `regex.Pattern` `Files.lines` `flatMap` `Collectors` `stream`
### （2） csvDeduplicateAndMerge
CSV文件去重合并  
`BufferedReader` `BufferedWriter` `StandardCharsets` `split` `LinkedHashMap`
### （3） GenericUtils
泛型的使用
泛型方法类型参数申明`<T, R>`；有界的类型参数`? super T``? extends R`  
`Pair<L, R>`  
`Function`接口：Lambda表达式`->`;方法引用`::`;匿名内部类实例化  
### （4） logCleaning
提取日志    
`LocalDateTime`解析：`DateTimeFormatter.ofPattern``LocalDateTime.parse`  
### （5） ExceptionWrapping
异常包装  
`Throwable`：`Error``Exception`  
`Exception`：`RuntimeException`(Unchecked Exception);Checked Exception(`IOException``SQLException``FileNotFoundException``ClassNotFoundException`)  
包装：`try``catch``throw new CustomException`其中`CustomException extends RuntimeException`  
  
## 4. Functional Programming
*函数式编程与核心特征*  
*函数式接口（Predicate<T> Function<T, R> Supplier<T> Consumer<T> UnaryOperator<T>） Lambda表达式(可以捕获effectively final的外部变量) 闭包*  
*`Stream`:`list.stream`,`Stream.of`,`Stream.iterate`,`Stream.generate`,`Arrays.stream`,`IntStream`,`filter`,`map`,`flatmap`,`distinct`,`sorted`,`peek`,`limit`,`skip`,`forEach`,`collect`,`reduce`,`anyMatch`,`IntSummaryStatistics`(`IntStream`),`parallelStream`*  
*`Collectors`:`groupingBy`,`partitioningBy`,`toMap`,`joining`,`summarizing`,`mapping`*  
*`OptionL`:`Optional.of`,`Optional.ofNullable`,`Optional.empty`,`ifPresent`,`orElse/orElseGet/orElseThrow`,链式操作`.map`,`.filter`*  
### (1) logSummary
日志明细汇总  
`groupingBy`,`summarizingDouble`  
### (2) courseFlatten
课程`flatMap`去重
### (3) filterAndSort
给定关键词进行过滤与排序  
`filter``sorted``Comparator`
### (4) OptionalExample
`Optional`处理空值  
### (5) toMapConfilct
处理`toMap`的键冲突  
  
## 5. Thread and Concurrent
*多线程与并发*  
*创建线程：`Runnable``Callable``ExecutorService`(`ThreadPoolExecutor``ArrayBlockingQueue`)*  
*同步与锁:`Synchronized``ReentrantLock`(公平锁：`new ReentrantLock(true)`;可轮询`.tryLock()`;可中断`.lockInterruptibly`;条件队列`lock.newCondition`:`await``awaitUninterruptibly``awaitNanos``signal``signalAll`)`ReentrantReadWriteLock`(读锁共享，写锁、读写锁之间互斥，单线程允许写锁降级为读锁，不允许锁升级防止死锁)*  
*可见性与原子性：`volatile`（可见性、有序性）;`AtomicInteger``AtomicReference``AtomicBoolean``compareAndSet``LongAdder`（可见性、原子性）*  
*并发集合：`ConcurrentHashMap``CopyOnWriteArrayList``BlockingQueue`(`ArrayBlockingQueue``LinkedBlockingQueue``ConcurrentLinkedQueue`)*  
*`CompletableFuture`:`supplyAsync``thenApply``thenAccept``thenCompose``exceptionally`*  
### (1) ThreadPoolBatch  
任务批处理  
`Executors.newFixedThreadPool``Callable``future.get()`  
### (2) ProducerAndConsumer
生产者与消费者  
`LinkedBlockingQueue``put/take`  
### (3) AtomicVsSynchronized
`AtomicInteger` 与 `synchronized` 的性能比较  
### (4) CompletableFutureCombine  
`thenCombine`、`exceptionally`
  
# MavenProject  
## 1. 构建日志工具与测试  
### (1) 基于jdk8的Maven项目构建  
### (2) SLF4J与logback的配置与实现  
*日志门面与实现*
### (3) JUnit5与Mockito的配置与实现  
*单元测试与解耦合*
### (4) Lombok的配置与实现  
*注解简化代码*
### (5) surefire/failsafe插件  
*单元测试与集成测试*  
  
## 2. Web与REST  
*建立学生管理API（见`package HTTP`）+Postman发送请求*
### (1) REST资源URI  
`/students` `/students/{studentId}` `/students/{studentId}/enrollments`  
查询`?page=0&size=5&sort=enrollmentDate,desc&name=Ali`  
方法：GET POST PUT PATCH DELETE  
bash:  
curl -X POST "http://localhost:8080/api/v1/students" \  
-H "Content-Type: application/json" \  
-d '{"name": "Alice", "email": "alice@example.com"}'  
### (2) 错误响应  
统一错误响应体：  
public class ErrorResponse {
    private Instant timestamp;
    private String path;
    private String code;
    private String message;
    private Object details;
}  
常见错误码：  
MISSING_REQUIRED_FIELD 400 缺少必填字段  
UNAUTHORIZED 401 未认证或认证失败  
FORBIDDEN 403 无权限访问资源  
RESOURCE_NOT_FOUND 404 资源不存在  
CONFLICT 409资源冲突  
### (3) Postman CRUD  
`baseUrl:http://localhost:8080/api/v1`  
请求e.g.:  
POST：`{{baseUrl}}/students`  
断言：`pm.test("Status 201", () => pm.response.to.have.status(201));`  
### (4) 分页/过滤  
bash:
curl "http://localhost:8080/api/v1/students?page=0&size=20"  
curl "http://localhost:8080/api/v1/students?page=1&size=10&name=Alice"  
curl "http://localhost:8080/api/v1/students?page=0&size=15&name=Bob&sort=enrollmentDate,desc"  

## 3. MySQL  
### (1) ER建模
涉及的SQL的DDL（建表、建索引）和DML（测试约束）操作通过JDBC连接到MySQL实现（见`CreatTables.java`）  
### (2) 统计查询  
JDBC实现于`StudentCourseStats.java`
`group by`:`select`只能使用聚合函数及`group by`引用的字段  
`having`作用于分组后的聚合值  
`inner join`只返回两表都有匹配记录的行；`left join`返回左表所有记录及右表匹配记录  
### (3) 事务演示  
JDBC实现于`TransactionDemo.java`  
脏读：`Read Uncommitted`可发生，`Read Committed`及以上隔离级别不可发生；  
不可重复读：`Read Committed`及以下隔离级别可发生；  
幻读：`Repeatable Read`及以下隔离级别可发生， InnoDB 通过间隙锁解决了`Repeatable Read`可能幻读的问题；  
### (4) 索引对比  
JDBC实现于`IndexComparison.java`，关注`explain`给出的查询信息  
  
## 4. JDBC与ORM  
*JDBC：HikariCP进行连接管理，`prepareStatement`：提高多次执行效率与安全性*  
*JPA：实体映射`@Entity` `@Table` `@Id @GeneratedValue(strategy=GenerationType.IDENTITY)`；关系映射`@OneToMany` `@ManyToOne` 懒加载导致N+1次查询 `join fetch`;JpaRepository；`@Query`*  
*MyBatis：xml自动映射；动态SQL；一级和二级缓存*  
### (1) CRUD  
JDBC预编译`prepareStatement`；JPA`JpaRepository`减少DAO层代码量；MyBatis`Mapper.xml`映射  
### (2) 分页与模糊查询  
JPA:`findByNameContaining``PageRequest`；MyBatis：`LIMIT #{offset}, #{size}`，实际业务考虑一致性可游标分页  
### (3) OneToManyExample  
JPA:`@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)`通过`JOIN FETCH`避免N+1；Session 关闭后访问懒加载属性会抛异常，本例通过`@Transactional`事务边界覆盖解决  
MyBatis:`association`建立一对一关联，`collection`建立一对多关联，确保属性名与列名匹配  
  
# SpringBootProject  
## 1. SpringCore与SpringBoot入门  
### (1) HelloAPI  
`HelloController.java`  
### (2) 参数校验  
`StudentCreateRequest.java`:`@NotBlank`,`@NotNull`,`@Size`;`StudentCotroller.java`:`@PostMapping`;  
`ErrorResponse.java`;`GlobalExceptionHandler.java`:`@ExceptionHandler(MethodArgumentNotValidException.class)`  
### (3) 配置绑定  
`application.properties`;`AppProperties.java`:`@ConfigurationProperties(prefix = "XXX")``@Component`  
