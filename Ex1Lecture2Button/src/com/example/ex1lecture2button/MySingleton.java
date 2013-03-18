package com.example.ex1lecture2button;



public class MySingleton{

	private int i=0;
	private static MySingleton singleton;
	// Returns the application instance
	public static  MySingleton getInstance() {
		if(singleton==null)
		{
			singleton = new MySingleton();
		}
	return singleton;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	
	
	
}
