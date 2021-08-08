package com.example;


public class MyClass<T> {
	
	private T t;

	
	public MyClass() {
		super();
	}
	
	public MyClass(T t) {
		super();
		this.t = t;
	}
	

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public String defineT() {
		
		
		if (t.getClass() == String.class) {
			return "t is a String!";
		} else if (t.getClass() == Integer.class) {
			return "t is an Integer!";
		} else if (t.getClass() == Boolean.class) {
			return "t is a Boolean!";
		}
		
		return "t is not String, nor Integer, nor Boolean!";
		
	}
	
}
