# Java-Learning
This is a repository for Java learning.
## StudentSystem  
*登陆注册 增删查改*  
基础语法
## OOPExercise  
*面向对象编程练习*
### BankAccount  
银行账户系统建模 活期定期账户存取款与利息计算  
多态 重写 抽象类  
### AreaCalculator
面积计算器  
接口多态  
### UserDeduplication  
`HashSet`与`TreeSet`去重  
重写`equals`与`hashCode`（`HashSet`）  
重写`compareTo`（`TreeSet`）
### OrderStatus
订单状态转移  
枚举`enum` `final`
## IOAndException
*核心类库 IO Exception*
### WordFrequency
文件中单词频率统计  
`IOException` `Comparator` `regex.Pattern` `Files.lines` `flatMap` `Collectors` `stream`
### csvDeduplicateAndMerge
CSV文件去重合并  
`BufferedReader` `BufferedWriter` `StandardCharsets` `split` `LinkedHashMap`
### GenericUtils
泛型的使用
泛型方法类型参数申明`<T, R>`；有界的类型参数`? super T``? extends R`  
`Pair<L, R>`  
`Function`接口：Lambda表达式`->`;方法引用`::`;匿名内部类实例化  
### logCleaning
提取日志    
`LocalDateTime`解析：`DateTimeFormatter.ofPattern``LocalDateTime.parse`  
### ExceptionWrapping
异常包装  
`Throwable`：`Error``Exception`  
`Exception`：`RuntimeException`(Unchecked Exception);Checked Exception(`IOException``SQLException``FileNotFoundException``ClassNotFoundException`)  
包装：`try``catch``throw new CustomException`其中`CustomException extends RuntimeException`
