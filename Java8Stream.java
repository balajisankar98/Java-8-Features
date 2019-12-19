package com.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class will cover most of the feautures of java 8 Stream
 * @author Balaji Sankar
 *
 */
public class Java8Stream implements Consumer<String>{
	
	private class Student {

		private String name;
	    private Set<String> book = new HashSet<>();

	    public void addBook(String book) {
	        this.book.add(book);
	    }

		public Student(String name) {
			super();
			this.name = name;
		}

		public Student() {
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Set<String> getBook() {
			return book;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", book=" + book + "]";
		}

	}
	
	
	
	public static List<String> addEntries() {
		List<String>  list = new ArrayList<>();
		list.add("adam");
		list.add("adam");
		list.add("eva");
		list.add("johnathan");
		list.add("walter");
		list.add("abraham");
		list.add("abraham");
		list.add("abraham");
		return list;
	}
	
	public static void main(String[] args) {
		List<String> list = addEntries();
		//creating an arrayList
		
		Java8Stream streamEx =  new Java8Stream();
		//creating an object 
		
		 List<Student> studentList = addStudentEntries();
	        //creating a list of student object 
	        
		boolean result = list.stream().allMatch(s->s.contains("a"));
		 System.out.println("-------------allMatch()--------------");         
		System.out.println("allMatch ::"+result);//prints true .Checks whether all the entries matches the condition
		//Once stream has been created and used it can't be used again
		
		
		result = list.stream().anyMatch(s->s.contains("w"));
		 System.out.println("-------------anyMatch()--------------");         
		System.out.println("anyMatch ::"+result);//prints true .Checks whether any of the entries matches the condition
		
		
		result = list.stream().noneMatch(s->s.contains("z"));
		 System.out.println("-------------noneMatch()--------------");         
		System.out.println("noneMatch ::"+result);//prints true .Checks whether none of the entries matches the condition
		
		System.out.println("-------------distinct()--------------");
		list.stream().distinct().forEach(System.out::println);
		//distinct collects unique records (not equal objects)
		
		
		List<String> filteredList = list.stream().filter(s->s.startsWith("a")).collect(Collectors.toList());
		//filters the list based on the condition and collect is used to collect all the records and collectors.tolist() is used to return as list
		
		System.out.println("-------------filter()--------------");
		filteredList.forEach(streamEx);
		// .forEach will have parameter as any object as long as it implements Consumer interface 
		// consumer interface is functional interface having only one abstract method accept with any type as parameter
		// .forEach will execute whatever logic in the accept method of the object passed
		
		
		Long count = list.stream().filter(s->s.startsWith("a")).count();
		System.out.println("-------------count()--------------"); 
		System.out.println("count ::"+count);
		//prints the no of entries from the intermediate operation
		
		
		System.out.println("-------------findAny()--------------"); 
		list.stream().filter(s->s.startsWith("z")).findAny().ifPresent(System.out::println);
		//findAny() will return a optional to indicate that it may or may not contain 
		//an element since there is no element starting with z it will return null
		
		
		System.out.println("-------------findFirst()--------------"); 
		list.stream().filter(s->s.startsWith("a")).findFirst().ifPresent(System.out::println);
		// .findFirst will get the first element of the stream and return an optional element 
		
        
        Stream<Student> collect = studentList.stream();
        Stream<String>	flatMap = collect.map(Student::getBook)//Stream<Set<String>>
                        .flatMap(Set::stream)   //Stream<String>
                        .distinct();
        
        //.map is used to return a stream of the given object
        //.flatMap is used to add the stream of every element and combine it to a single stream.
        //like flatMap flatMapToInt,flatMapToDouble,flatMapToLong is used to create a stream of intStream ,doubleStream,longStream
        
        System.out.println("-------------flatMap()--------------");                
        flatMap.forEach(streamEx);
        //collect.forEach(System.out::println);
        //the above code will cause an error because once a stream is operated and closed it cannot be used again
        
        IntStream streamInt = list.stream().mapToInt(s->s.length());
        System.out.println("-------------mapToInt()--------------"); 
        streamInt.forEach(System.out::println);
        //IntStream is stream of primitive data type int . It also has parallel processing and sequential like stream and parallel stream
        //like mapToInt mapToDouble , mapToLong is used to create a stream of intStream ,doubleStream,longStream
        
        Stream<String> intStream = Stream.generate(new Random()::nextInt).limit(10).map(s->Integer.toString(s));
        System.out.println("-------------generate()--------------"); 
        intStream.forEach(System.out::println);
        //generate is used to get an infinite stream of objects usually 
        //limit is used to set a limit on how many elements to be collected
        
        System.out.println("-------------iterate()--------------"); 
        Stream.iterate(0, i -> i + 2).limit(10).forEach(System.out::println);
        // .iterate is used just like for loop 
        // 0 -> indicates that start of the loop 
        // i -> indicates the number of iterations
        // .limit is used limit the iteration
        
        
        System.out.println("-------------parallelStream()--------------"); 
        list.parallelStream().forEach(streamEx);
        // .parallelStream divide the provided task into many and run them in different threads,
        //utilizing multiple cores of the computer. On the other hand sequential streams work just like for-loop using a single core.
        //parallel stream executes the stream of objects asynchronously(multiThreading)
        
        
        System.out.println("-------------forEachOrdered()--------------"); 
        list.parallelStream().forEachOrdered(streamEx);
        //.forEachOrdered is used to make the stream iterate sequentially
        
        
        System.out.println("-------------iterator()--------------"); 
        Iterator<String> nameIter = list.stream().iterator();
        nameIter.forEachRemaining(streamEx);
        // .iterator is used to give iterator from the stream of objects
        // .forEachRemaining takes Consumer interface as object like .forEach 
        
        System.out.println("-------------max-String()--------------"); 
        list.stream().max(Comparator.naturalOrder()).ifPresent(System.out::println);
        
        
        System.out.println("-------------max-Integer()--------------"); 
        Stream.generate(new Random()::nextInt).limit(10).max(Integer::compare).ifPresent(System.out::println);
        //.max is used to compare the values based on the logic and gets the max value
        //max((x,y)->(x < y) ? -1 : ((x == y) ? 0 : 1)) :: x is the current max value and y is current element being iterated
        // if it is less means - 1 and equals is 0 or else if its greater 1 
        // it accepts -1 for lesser than and 1 for greater than and 0 for equals
        
        System.out.println("-------------min-String()--------------"); 
        list.stream().min(Comparator.naturalOrder()).ifPresent(System.out::println);
        
        
        System.out.println("-------------min-Integer()--------------"); 
        Stream.generate(new Random()::nextInt).limit(10).min((x,y)->(x < y) ? -1 : ((x == y) ? 0 : 1)).ifPresent(System.out::println);
        //.min returns the minimum value of the stream
        
        
        System.out.println("-------------onClose()--------------"); 
        list.stream().onClose(()->System.out.println("close is closed")).close();
        //.onClose method is called when close  method
        
        
        System.out.println("-------------peek()--------------"); 
        studentList.stream().peek(s->s.setName("hello - "+s.getName())).forEach(s->System.out.println(s.getName()));
        //.peek is used to change the objects state and not the objects type completly like .map
        //peek() can be useful in another scenario: when we want to alter the inner state of an element.
        //Alternatively, we could have used map(), but peek() is more convenient since we don't want to replace the element
        
        System.out.println("-------------map and peek differnce--------------");
        list.stream().map(x->x.toUpperCase()).forEach(streamEx);
        
        //this will change all the list of string to upperCase thereby changing the string object itself  
        //will create new object
        
        System.out.println("-------------map and peek differnce--------------");
        list.stream().peek(x->x.toUpperCase()).forEach(streamEx);
        
        //this wont change the list to uppercase peek just modifies the object and doesnot create new objects like map and since string
        //is immutable the list wont change
        //will change the state of the existing object
        
        System.out.println("-------------reduce()--------------"); 
        list.stream().reduce((p1,p2)->p1+"/"+p2).ifPresent(System.out::println);
        
        System.out.println("-------------reduce2()--------------"); 
        studentList.stream().map(p2->p2.getName()+"::"+p2.getBook()).
        		reduce((p1,p2)->p1+"/"+p2).ifPresent(System.out::println);
        
        System.out.println("-------------reduce3()--------------"); 
        Student studentObj = studentList.stream().reduce(new Java8Stream().new Student("Balaji"),(p1,p2)->{
        	p1.name =p1.name+"/"+p2.name;
        	p1.book.addAll(p2.book);
        	return p1;
        });
        System.out.println(studentObj);
        //.reduce is used reduce the stream of objects to a single object based on the logic provided
        // 2 ex has 2 parameters p1 is the base element or 1st element and p2 is the current iterated object
        // 3rd ex has 3 parameter it is returning a object the 1st param is a new object and it is the returned reduced object
        //,p1-> is the base object and initially 1st param object is  given
        //p2-> current iterated object
        
        
        System.out.println("-------------sequential()--------------"); 
        list.parallelStream().sequential().forEach(streamEx);
        // .sequential is used for making the stream iterate sequentially
        
        System.out.println("-------------sorted()--------------"); 
        list.stream().sorted().forEach(streamEx);
        
        System.out.println("-------------sorted natural()--------------"); 
        list.stream().sorted(Comparator.naturalOrder()).forEach(streamEx);
        
        System.out.println("-------------sorted reverse()--------------"); 
        list.stream().sorted(Comparator.reverseOrder()).forEach(streamEx);
        
        Stream.generate(new Random()::nextInt).limit(10).sorted((x,y)->(x < y) ? -1 : ((x == y) ? 0 : 1)).forEach(System.out::println);
        //sorted is used to return  sorted stream . 
        
        System.out.println("-------------unordered()--------------"); 
        list.stream().unordered().forEach(streamEx);
        //unordered is used to return unordered stream (may return the stream itself either because the stream was already unordered)
        
        
        System.out.println("-------------Collectors.groupingBy()--------------"); 
        studentList.stream().collect(Collectors.groupingBy(Student::getName)).forEach((k,v )->System.out.println(k+"::"+v));
        //Collectors.groupingBy returns a map on whatever object we are mapping to the grouped by element becomes the key
        
        
        System.out.println("-------------Collectors.groupingBy()--------------");
        list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).forEach((k,v)->System.out.println(k+"::"+v));
        //returns a map
        //will combine the duplicate elements and give the no . of occurrence of each element
        
        
        System.out.println("-------------Collectors.groupingBy()--------------");
        list.stream().collect(Collectors.groupingBy(s-> "hello -"+s)).forEach((k,v)->System.out.println(k+"::"+v));
        //grouped by a custom string
        
        System.out.println("-------------Collectors.groupingByConcurrent()--------------");
        list.stream().collect(Collectors.groupingByConcurrent(s-> "hello -"+s)).forEach((k,v)->System.out.println(k+"::"+v));
        //groupingByConcurrent executes in parallel
        
        System.out.println("-------------Collectors.maxBy()--------------");
        System.out.println(Stream.iterate(1, i->i*2).limit(10).collect(Collectors.maxBy((x,y)->(x < y) ? -1 : ((x == y) ? 0 : 1))).toString());
        //returns an max object according to comparator given
        
        
        System.out.println("-------------Collectors.minBy()--------------");
        System.out.println(Stream.iterate(1, i->i*2).limit(10).collect(Collectors.minBy((x,y)->(x < y) ? -1 : ((x == y) ? 0 : 1))).toString());
        //returns an minimum object according to comparator given
        
        
        System.out.println("-------------Collectors.averagingInt()--------------");
        System.out.println(Stream.iterate(1, i->i*2).limit(10).collect(Collectors.averagingInt(i->i)).toString());
        // .averagingInt gives average of the stream of integer
        
        System.out.println("-------------Collectors.summarizingInt()--------------");
        System.out.println(Stream.iterate(1, i->i*2).limit(10).collect(Collectors.summarizingInt(i->i)).toString());
        //summaries the list of integer to count , sum , min , max and average
        
        
        System.out.println("-------------Collectors.joining()--------------");
        System.out.println(list.stream().collect(Collectors.joining(" and ","In Germany ", " are of legal age.")));
        
        System.out.println("-------------Collectors.joining()--------------");
        System.out.println(list.stream().map(s->s+"/").collect(Collectors.joining()));
        //joining uses connector ,prfix and suffix to connect the list of string to single string
        //joining also joins a list to string directly
        
        System.out.println("-------------Collectors.of()--------------");
        System.out.println( list.stream().collect(Collector.of(
        () -> new StringJoiner(" | "),          // supplier
        (j, p) -> j.add(p.toUpperCase()),   // accumulator
        (j1, j2) -> j1.merge(j2),   // combiner
        StringJoiner::toString)));           // finisher
        
        System.out.println("-------------Collectors.partitioningBy()with count--------------");
        list.stream().collect(Collectors.partitioningBy(s->s.startsWith("a"), Collectors.counting())).forEach((k,v)->System.out.println(k+"::"+v));
        
        
        System.out.println("-------------Collectors.partitioningBy()--------------");
        list.stream().collect(Collectors.partitioningBy(s->s.startsWith("a"))).forEach((k,v)->System.out.println(k+"::"+v));
        //partitioningBy will partition the list based on the condition given to map<Boolean,List<String>>
        
        System.out.println("-------------Collectors.reducing()--------------");
        list.stream().collect(Collectors.reducing((s1,s2)->s1+" , "+s2)).ifPresent(streamEx);
        //reducing reduces the list to whatever type we desire
        //ifPresent also takes consumer as parameter
        
        System.out.println("-------------Collectors.toMap()--------------");
        Map<String,String> map = list.stream().distinct().collect(Collectors.toMap(k->k, v->v));
        map.forEach((k,v)->System.out.println(k+"::"+v));
        //Collectors.toMap is used to convert any stream to map it will return a key and value
        
        
        System.out.println("-------------Collectors.toMap()--------------");
        list.stream().
        		collect(Collectors.toMap(k->k,p -> p, (s, a) -> s + ", " + a,LinkedHashMap::new)).
        		forEach((k,v)->System.out.println(k+"::"+v));
        //if there are duplicate keys the values will added in comma separated values
        
        
        //there is no stream method in Maps
        System.out.println("-------------Collectors.entrySet.stream()--------------");
        map.entrySet().stream().
        		filter(e-> e.getKey().startsWith("a")).
        		collect(Collectors.toMap(e->e.getKey(),e->e.getValue())).
        		forEach((k,v)->System.out.println(k+"::"+v));
        //We can use stream in map using entrySet
        
	}

	@Override
	public void accept(String t) {
		System.out.println(t);
	}
	
	public static List<Student> addStudentEntries() {
		List<Student> students = new ArrayList<>();
		Student obj1 = new Java8Stream().new Student();
        obj1.setName("Johnathan");
        obj1.addBook("Java 8 in Action");
        obj1.addBook("Spring Boot in Action");
        obj1.addBook("Effective Java (2nd Edition)");

        Student obj2 = new Java8Stream().new Student();
        obj2.setName("Richard");
        obj2.addBook("Learning Python, 5th Edition");
        obj2.addBook("Effective Java (2nd Edition)");
        //creating objects for the inner class and adding entries for the object
        
        students.add(obj1);
        students.add(obj2);
        return students;
	}
}

