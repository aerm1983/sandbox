package com.example;

public class Box <T> {
	
	private T t;

	public Box() {
		super();
	}

	public Box(T t) {
		super();
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	
}
