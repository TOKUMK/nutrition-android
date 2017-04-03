package com.nutrition.intuition.model;



public class NutritionFactItem{
	
	private String 	nutrientTitle;
	private int 	nutrientValue;
	private int 	dailyPercentageValue;
	
	public NutritionFactItem(){}	
	
	public NutritionFactItem(String title, int nutrientVal, int percentageVal ){
		this.nutrientTitle 			= title;
		this.nutrientValue 			= nutrientVal;
		this.dailyPercentageValue 	= percentageVal;
	}

	
	public String getNutrientTitle() {
		return nutrientTitle;
	}

	public int getNutrientValue() {
		return nutrientValue;
	}

	public int getDailyPercentageValue() {
		return dailyPercentageValue;
	}

	public void setNutrientTitle(String nutrientTitle) {
		this.nutrientTitle = nutrientTitle;
	}

	public void setNutrientValue(int nutrientValue) {
		this.nutrientValue = nutrientValue;
	}

	public void setDailyPercentageValue(int dailyPercentageValue) {
		this.dailyPercentageValue = dailyPercentageValue;
	}

	@Override
	public String toString() {
		return "NutritionFactItem [nutrientTitle=" + nutrientTitle
				+ ", nutrientValue=" + nutrientValue
				+ ", dailyPercentageValue=" + dailyPercentageValue + "]";
	}


	
}