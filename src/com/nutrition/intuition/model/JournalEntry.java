package com.nutrition.intuition.model;



public class JournalEntry{
	
	private String 	foodName;
	private int 	servingSize;
	private String 	time;
	
	public JournalEntry(){}	

	public JournalEntry(String foodName, int servingSize, String time ){
		this.foodName = foodName;
		this.servingSize = servingSize;
		this.time = time;
	}

	public String getFoodName() {
		return foodName;
	}

	public int getServingSize() {
		return servingSize;
	}

	public String getTime() {
		return time;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "JournalEntry [foodName=" + foodName + ", servingSize="
				+ servingSize + ", time=" + time + "]";
	}

	
}