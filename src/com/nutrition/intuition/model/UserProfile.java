package com.nutrition.intuition.model;

import java.io.Serializable;


public class UserProfile implements Serializable{
	
	private String 	name;
	private int 	age;
	private String	gender;
	private int 	weight;
	private int 	height;
	private String 	diet;
	private String  exercise;

	public UserProfile(){}
	
	public UserProfile(String name, int age, String gender, int weight, int height, String diet, String exercise){
		this.name 		= name;
		this.age  		= age;
		this.gender 	= gender;
		this.weight 	= weight;
		this.height 	= height;
		this.diet 		= diet;
		this.exercise   = exercise;
	}

	@Override
	public String toString() {
		return "UserProfile [name=" + name + ", age=" + age + ", gender="
				+ gender + ", weight=" + weight + ", height=" + height
				+ ", diet=" + diet + ", exercise=" + exercise + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	

	
}
