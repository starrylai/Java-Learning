# Java-Learning
This is a repository for Java learning.  
  
## 1、StudentSystem  
*登陆注册 增删查改*  
基础语法  
  
## 2、OOPExercise  
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
  
## 3、IOAndException
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
  
## 4、Functional Programming
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

