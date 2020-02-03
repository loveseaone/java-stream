package org.java.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import junit.framework.TestCase;

public class AppTestStream extends TestCase{

	
	
	public void teststream() {
		Stream<Integer> a=Stream.of(1,2,3);
		Stream<Integer> b=Stream.of(3,4,5);
		 
	    Optional<Integer> e=	Stream.concat(a, b).findFirst();
	    System.out.println(e.toString());
	    Optional<Integer> e1 =Stream.concat(a, b).findAny();
	    System.out.println(e1.toString());
	    String values[] =new String[] {"a","b","c"};
	    Stream.of(values);
	    List<Integer> ls=new ArrayList<Integer>();
	    ls.parallelStream().count();
	    
	}
	
	public void testmap() {
		 List<Student> list=load();
		 Map<Integer, List<Student>> map=new HashMap<Integer, List<Student>>();
		 map.put(1, list);
		 map.entrySet().parallelStream().map(e->{
			return e.getValue();
		 }).collect(Collectors.toList()).forEach(System.out::println);
		 
		 map.entrySet().parallelStream().flatMap(e->{
				return e.getValue().stream();
			 }).collect(Collectors.toList()).forEach(System.out::println);
		 
		 map.entrySet().parallelStream().flatMap(e->{
				return e.getValue().stream();
			 }).collect(Collectors.toSet()).forEach(System.out::println);
		 
		  
	}
	
	/**
	 * forEachOrdered forEach 只有在并行模式有区别
	 */
	 public void  testForeach() {
		 
		 List<String> strs = Arrays.asList("a", "b", "c");
	     strs.stream().forEachOrdered(System.out::print);//abc
	     System.out.println();
	     strs.stream().forEach(System.out::print);//abc
	     System.out.println();
	     strs.parallelStream().forEachOrdered(System.out::print);//abc
	     System.out.println();
	     strs.parallelStream().forEach(System.out::print);//bca
	 }
	 
	 /**
	  * reduce  3个参数 在不同模式下，执行结果不一样
	  */
	 public void testreduce() {
		 
		List<Integer> numbers = Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.toList());
		Integer d1 = 0;
		for (Integer i : numbers) {
			d1 += i;
		}
		 
		 
	   Optional<Integer> d2 = numbers.stream().reduce((a, b) -> a + b);
	   Integer d3 = numbers.stream().reduce(0, (a, b) -> a + b);
	   Integer d4 = numbers.stream().reduce(0, (a, b) -> a + b,(a,b)->a-b);
	   System.out.println(d1);
      
       System.out.println(d2.get());
       System.out.println(d3);
       System.out.println(d4);
       
	 }
	 
	 public void testreduce2() {
		 
			List<Integer> numbers = Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.toList());
			Integer d1 = 0;
			for (Integer i : numbers) {
				d1 += i;
			}
			 
		   Optional<Integer> d2 = numbers.parallelStream().reduce((a, b) -> a + b);
		   Integer d3 = numbers.parallelStream().reduce(0, (a, b) -> a + b);
		   Integer d4 = numbers.parallelStream().reduce(0, (a, b) -> a + b,(a,b)->a-b);
		   System.out.println(d1);
	      
	       System.out.println(d2.get());
	       System.out.println(d3);
	       System.out.println(d4);
		 }
	/**
	 * 查找学号 100-200 并打印 
	 */
	public void testfilter() {
		 
		List<Student> students =load();
		students.stream().filter(d->{
			return d.getSno()>100 && d.getSno()<201 ;
		}). forEach(d->System.out.println(d));
		
	}
	/**
	 * 统计重名的学生  并打印 
	 */
	public  void  testgroupingBy() {
		 List<Student> students =load();
		 students.stream().collect(Collectors.groupingBy(Student::getName))
		 .forEach((k,v)->{
			 System.out.println(k);
			 v.forEach(System.out::println);
		 });
	}
	
	public  void  testdistinct() {
		Student s1 = new Student("Bob",1);
        Student s2 = new Student("Jim",2);
        Student s3 = new Student("Tom",3);
        Student s4 = new Student("Jack",4);
		List<Student> list0 = new ArrayList<>();
		list0.add(s1);
		list0.add(s2);
		list0.add(s3);
		List<Student> list1 = new ArrayList<>();
		list1.add(s1);
		list1.add(s2);
		list1.add(s4);  
		list1.add(new Student("Jack",4));
		List<Student>  list =Stream.of(list0,list1).flatMap(Collection::stream).distinct().collect(Collectors.toList());
		list.forEach(System.out::println);
	}
	 
	public  void  testmax() {
		 List<Student> students =load();
		 students.stream().max(new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				 
				if( o1.getSno()>o2.getSno()) {
					return 1;
				}else if(o1.getSno()<o2.getSno()) {
					return -1;
				}else {
					return 0;
				}
			}
		}).stream().forEach(System.out::println);; 
		 
		 
	}
	
	public  void  testmin() {
		 List<Student> students =load();
		 
		 
		 students.stream().min(new Comparator<Student>() {
				@Override
				public int compare(Student o1, Student o2) {
					 
					if( o1.getSno()>o2.getSno()) {
						return 1;
					}else if(o1.getSno()<o2.getSno()) {
						return -1;
					}else {
						return 0;
					}
				}
			}).stream().forEach(System.out::println);
	}
	
	public  void  testcollect() {
		 List<Student> students =load();
		 //拼接成一个字符串
		 String result =students.stream().map(s->{
			 return s.getSno()+"" ;
		 }).collect(Collectors.joining(",", "[", "]"));
		 System.out.println(result);
		 //求对象某一数值域平均值
		 double aver= students.stream(). collect(Collectors.averagingInt(Student::getSno));
		 System.out.println(aver);
		 //求对象某一数值域总和
		 double sum = students.stream(). collect(Collectors.summingInt(Student::getSno));
		 System.out.println(sum);
		 //求对象某一数值域统计数据
		 IntSummaryStatistics statics =students.stream(). collect(Collectors.summarizingInt(Student::getSno));
		 System.out.println(statics);
		 //按照某一对象域分类
		Map<String, List<Student>> groupby= students.stream().collect(Collectors.groupingBy(Student::getName));
		 System.out.println(groupby);
		 //按某一条件区分
		Map<Boolean, List<Student>> partby =students.stream().collect(Collectors.partitioningBy(s->{
			 return s.getSno()>5 && s.getSno()<10;
		 }));
		System.out.println(partby);
	}
	 
	
	private  List<Student> load(){
		List<Student> students=new ArrayList<Student>(10000);
		
		for(int i=1;i<=10;i++) {
			students.add(new Student("name"+i, i));
		}
		for(int i=1;i<=10;i++) {
			students.add(new Student("name"+i, 1000+i));
		} 
		return students;
	}

	class Student{
		
		public Student(String name,int sno) {
			this.name=name;
			this.sno=sno;
		}
		
		String name ;
		
		int sno ;
		
		 
		public int getSno() {
			return sno;
		}
		public void setSno(int sno) {
			this.sno = sno;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			 
			return name+":"+sno;
		}
		
		
		
	}
}
