package localhost.sandbox.JavaLang.ScratchPad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaLangScratchPadMain {

	public static void main() {
		
		System.out.println("Hello from JavaLangScratchPadMain!");
		
		
		
		List<String> stringList = new ArrayList<>();
		System.out.println("stringList.getClass(): " + stringList.getClass());
		stringList.add("hello");
		
		if ( stringList.get(0) instanceof String  ) {
			System.out.println("stringList.get(0) instanceof String : true");
			System.out.println("stringList.get(0).getClass() : " + stringList.get(0).getClass());
		}
		
		
		
		
		
		/*
		long t1 = System.currentTimeMillis() ;
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis() ;
		
		float diffSecs = (float) ( (t2-t1) / 1000.0 ) ;
		
		System.out.println("t1:" + t1 + " ; t2: " + t2 + " ; diffSecs: " + diffSecs);
		*/
		
		
		
		
		
		/*
		for (int i = 0 ; i < 100*1000 ; i++ ) {
			
			if ( (i % 1000) == 0) {
				System.out.println("i: " + i);	
			}
			
		}
		*/
		
		
		
		
		
		
		
		
		/*
		String cumm = "";
		cumm += String.format("string: %s ; int: %s ; long: %s ; boolean: %s ; double: %s ----", "hi!", 1, 10L, false, 3.1416);
		System.out.println(cumm);
		*/
		
		
		/*
		String ts1 = String.format("Download error, file empty -- fileName: %s ; storeId: %s ; job: %s", "a", "b", 1);
		System.out.println(ts1);
		*/
		
		
		
		/*
		String listStr = "alberto,mary,nena,josefina".toLowerCase();
		String[] listArr = listStr.split(",");
		Set<String> listSet = new HashSet<String>();
		listSet.addAll(Arrays.asList(listArr));
		System.out.println("listSet: " + listSet);
		System.out.println("alberto: " + listSet.contains("alberto"));
		System.out.println("diego: " + listSet.contains("diego"));
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		/* 
		String s = "hello";
		String s2 = "hello";
		
		if (s!=null && s.equalsIgnoreCase(s2)) {
		    System.out.println("one!");
		} else if (s!=null && s.equalsIgnoreCase(s2)) {
		    System.out.println("two!");
		}
		*/
		
		
		
		/*
		String s = "Alberto";
		
		System.out.println("s.getClass(): " + s.getClass() );
		
 
		PersonDetail personDetail = new PersonDetail();
		
		personDetail.name = "Alberto";
		
		if (personDetail == null || personDetail.name == null) {
			System.out.println("personDetail or personDetail.name is null");
		}
		
		if (personDetail != null && personDetail.name != null) {
			System.out.println("personDetail and personDetail.name are different to null");
		}
		*/
		
	}
	
}	
