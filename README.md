# java-stream
stream API 详解

 
在传统的多线思维下，编写并行的代码难度是比较大的，需要掌握的知识点比较多，涉及到知识点比较多，包括线程、线程池、fork/join、锁机制等框架。目的都是为了提高代码的执行效率，提高多核CPU的优势。

JDK1.8以后，出现了流计算框架，一切都变得相对很容易。但是，在实际应用中需要注意，慎用，少用，线程是不可控，也是有并发安全问题的，尽管能提升执行效率。

涉及知识点：

1. 流的构造  
2. 流的转换  集合(List,Set,Map)、数组与流之间的转化 
3. 特殊流包装类  IntStream、LongStream、DoubleStream
4. 流的操作 
	中间操作：filter、 distinct、 limit、 skip、 map、flatmap、sorted
	终端操作：anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、forEach、 forEachOrdered、  reduce、 collect、 count

实战心法：

filter-map-reduce 过程，并且终端操作不能再使用流，需要重新创建一个新的流对象。


stream流独有的特性：

1.  不支持索引访问
2.  每次转换原有Stream对象不改变，返回一个新的Stream对象
3.  不是数据结构，也不是数据存储

并行流使用的风险，注意线程安全，理解fork-join任务拆分模式


		/********10 + 1 + 2 + 3 = 16******/
		int reducedParams = Stream.of(1, 2, 3)
				.reduce(10, (a, b) -> a + b, (a, b) -> {
				 return a + b;
				});
		System.out.println(reducedParams);
				
		/********12 + 13 = 25; 25 + 11 = 36******/
		int reducedParallel =  Stream.of(1, 2, 3).parallel()
			    .reduce(10, (a, b) -> a + b, (a, b) -> {
			       return a + b;
			    });
		
		System.out.println(reducedParallel);
		
		通过以上示例理解fork-join,并行运算总是需要拆分到最小单元，而且是递归拆解。


影响并行流性能的主要因素：
1.	源数据结构
2.	装箱
3.	核的数量
4.	数据大小
5.	执行顺序(中间操作的执行顺序  )


 