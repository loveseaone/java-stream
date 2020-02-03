package org.java.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.util.StopWatch;

import junit.framework.TestCase;

public class AppTestParallelStream extends TestCase {

	public void test1() {
		
		AtomicInteger accumulateCount = new AtomicInteger(0);
		List<Integer> numbers= IntStream.range(1, 1000000).boxed().collect(Collectors.toList());
		StopWatch stopWatch  = new StopWatch("parallelStream");
		
		stopWatch.start();
		Integer reduceResult = numbers.parallelStream().reduce(0, (a, b) -> {
			accumulateCount.incrementAndGet();
			 
			return a+b;
		} );
		stopWatch.stop();
		System.out.println("reduceResult:"+reduceResult);
		System.out.println("accumulateCount:"+accumulateCount);
		System.out.println(stopWatch.prettyPrint());
	}
	public void test2() {
		
		AtomicInteger accumulateCount = new AtomicInteger(0);
		
		List<Integer> numbers= IntStream.range(1, 1000000).boxed().collect(Collectors.toList());
		
		StopWatch stopWatch  = new StopWatch("stream");
		stopWatch.start();
		Integer reduceResult = numbers.stream().reduce(0, (a, b) -> {
			accumulateCount.incrementAndGet();
			 
			return a+b;
		} );
		stopWatch.stop();
		System.out.println("reduceResult:"+reduceResult);
		System.out.println("accumulateCount:"+accumulateCount);
		System.out.println(stopWatch.prettyPrint());
	}
	 
	public void test3() {
	    for (int i = 0; i < 10; i++) {
	        List<String> list1 = new  ArrayList<String>();
	        List<String> list2 = new  ArrayList<String>();
	        list1.add("a");
	        list1.add("b");
	        list1.add("c");
	        list1.add("d");
	        list1.parallelStream().forEach(list->list2.add(list));
	        System.out.println(list2.size());

	    }
	}
	public void test4() {
	    for (int i = 0; i < 10; i++) {
	        List<String> list1 = new CopyOnWriteArrayList<String>();
	        List<String> list2 = new CopyOnWriteArrayList<String>();
	        list1.add("a");
	        list1.add("b");
	        list1.add("c");
	        list1.add("d");
	        list1.parallelStream().forEach(list->list2.add(list));
	        System.out.println(list2.size());

	    }
	}
	
	
	public void test5() {
		/********10 + 1 + 2 + 3 = 16******/
		int reducedParams = Stream.of(1, 2, 3)
				.reduce(10, (a, b) -> a + b, (a, b) -> {
				 return a + b;
				});
		/********12 + 13 = 25; 25 + 11 = 36******/
		int reducedParallel =  Stream.of(1, 2, 3).parallel()
			    .reduce(10, (a, b) -> a + b, (a, b) -> {
			       return a + b;
			    });
		System.out.println(reducedParams);
		System.out.println(reducedParallel);
	}
}
