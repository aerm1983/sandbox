package com.example;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;



public class Sandbox {

	static Gson gson = new Gson();
	


	public static void main(String[] args) {
		
		
		Double x = 22.9;
		
		Double _ceil = Math.ceil(x);
		
		Long _round = Math.round(x);
		
		System.out.println("ceil: " + _ceil.toString());
		System.out.println("round: " + _round.toString());
		
		
		
		
		
		
		/*
		
		Son son = new Son();
		son.setName("Alberto");
		son.setAge(37);
		son.setIsMale(true);
		
		String sonJson = gson.toJson(son);
		System.out.println("son: " + son + " ; gson.toJson(son): " + sonJson);
		
		
		
		try {
			Object o = gson.fromJson(sonJson, Object.class);
			System.out.println("o: " + o + " ; o.getClass(): " + o.getClass());
			
			String oJson = gson.toJson(o);
			System.out.println("gson.toJson(o): " + oJson);
			
			Son sonFromO = (Son) o;
			System.out.println("sonFromO: " + sonFromO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			Object o2 = gson.fromJson("\"hello world\"", Object.class);
			System.out.println("o2: " + o2 + " ; o2.getClass(): " + o2.getClass());
			
			String o2Json = gson.toJson(o2);
			System.out.println("gson.toJson(o2): " + o2Json);
			
			String stringFromO2 = (String) o2;
			System.out.println("strongFromO2: " + stringFromO2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		


		try {
			Object o3 = gson.fromJson("[\"Alberto\",\"Elias\"]", Object.class);
			System.out.println("o3: " + o3 + "o3.getClass(): " + o3.getClass());
			
			String o3Json = gson.toJson(o3);
			System.out.println("gson.toJson(o3): " + o3Json);
			
			String stringFromO3 = (String) o3;
			System.out.println("stringFromO3: " + stringFromO3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		
		
		try {
			Object o = gson.fromJson(sonJson, Object.class);
			ResponseEntity<?> re = new ResponseEntity<>(o, HttpStatus.OK);
			System.out.println("re: " + re + " ; re.getBody(): " + re.getBody() + " ; re.getBody().getClass(): " + re.getBody().getClass());
			
			ResponseEntity<?> re2 = new ResponseEntity<>(son, HttpStatus.OK);
			System.out.println("re2: " + re2 + " ; re2.getBody(): " + re2.getBody() + " ; re2.getBody().getClass(): " + re2.getBody().getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		
		
		
		/*
		String errorMessage = "{\"errorResponse\":{\"status\":403,\"message\":\"Visa declined this transaction. If you see this too frequently, and you think you are getting this in error please contact the issuer.\",\"reason\":\"cardNotEligible\"}}";
		System.out.println("errorMessage: " + errorMessage);
		if ( errorMessage.matches("(?i).*cardNotEligible.*") ) {
			System.out.println("match!");
		} else {
			System.out.println("no match!");
		}
		*/
		
		
		/*
		Box<?> box = new Box<String>("hello world");
		System.out.println("box: " + box);
		System.out.println("box.getClass(): " + box.getClass());
		System.out.println("box.getT(): " + box.getT());
		System.out.println("box.getT().getClass(): " + box.getT().getClass());
		*/
		
		
		/*
		box = new Box<>();
		System.out.println("box: " + box + " ; box.getT(): " + box.getT());
		System.out.println("box.getT().getClass(): " + box.getT().getClass());
		*/
		
		
		
		
		/*
		MyClass<String> mc1 = new MyClass<>();
		
		mc1.setT("Hi there!");
		
		MyClass<Integer> mc2 = new MyClass<>();
		
		mc2.setT(1);
		
		MyClass<Boolean> mc3 = new MyClass<>();
		
		mc3.setT(true);
		
		
		System.out.println( "gson.toJson(mc1): " + gson.toJson(mc1) + "; " + mc1.defineT() );
		System.out.println( "gson.toJson(mc2): " + gson.toJson(mc2) + "; " + mc2.defineT() );
		System.out.println( "gson.toJson(mc3): " + gson.toJson(mc3) + "; " + mc3.defineT() );
		
		
		
		Object o1 = (Object) mc1;
		System.out.println( "gson.toJson(o1): " + gson.toJson(o1) + "; " + o1.getClass() );
		*/
		
		

		/*
		Son s = new Son();
		s.setName("alberto");
		s.setAge(37);
		s.setMale(true);
		
		
		// System.out.println("gson.toJson(s): " + gson.toJson(s) + " ; " + s.getClass() + " ; " + s);
		// Object os = (Object) s;
		// System.out.println("gson.toJson(os): " + gson.toJson(os) + " ; " + os.getClass() + " ; " + os);
		
		
		String sS = gson.toJson(s);
		
		System.out.println( "sS : " + sS );
		
		String sS2 = gson.fromJson(sS, String.class);
		
		System.out.println( "sS2 : " + sS2 );
		
		*/
		
		
		
		
		
		
		/*
		Father f = (Father) s;
		System.out.println("gson.toJson(f): " + gson.toJson(f) + " ; " + f.getClass() + " ; " + f);
		Object of = (Object) f;
		System.out.println("gson.toJson(of): " + gson.toJson(of) + " ; " + of.getClass() + " ; " + of);
		f = gson.fromJson(gson.toJson(s), f.getClass());
		System.out.println("gson.toJson(f): " + gson.toJson(f) + " ; " + f.getClass() + " ; " + f);
		f = gson.fromJson(gson.toJson(s), Father.class);
		System.out.println("gson.toJson(f): " + gson.toJson(f) + " ; " + f.getClass() + " ; " + f);


		Grandfather g = (Grandfather) s;
		System.out.println("gson.toJson(g): " + gson.toJson(g) + " ; " + g.getClass() + " ; " + g);
		Object og = (Object) g;
		System.out.println("gson.toJson(og): " + gson.toJson(og) + " ; " + og.getClass() + " ; " + og);
		g = gson.fromJson(gson.toJson(s), g.getClass());
		System.out.println("gson.toJson(g): " + gson.toJson(g) + " ; " + g.getClass() + " ; " + g);
		g = gson.fromJson(gson.toJson(s), Grandfather.class);
		System.out.println("gson.toJson(g): " + gson.toJson(g) + " ; " + g.getClass() + " ; " + g);

		
		
		Daughter d = (Daughter) (Object) s;
		System.out.println("gson.toJson(d): " + gson.toJson(d) + " ; " + d.getClass() + " ; " + d);
		Object od = (Object) d;
		System.out.println("gson.toJson(od): " + gson.toJson(od) + " ; " + od.getClass() + " ; " + od);
		*/
		
	}

}
