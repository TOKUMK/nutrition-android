package com.nutrition.intuition.model;

import java.io.Serializable;
import java.util.HashMap;

public class UserReport implements Serializable{
	
	String name;
	HashMap<String, Integer> data;
	
	
	public String getName() {
		return name;
	}
	public HashMap<String, Integer> getData() {
		return data;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setData(HashMap<String, Integer> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}