package com.nutrition.intuition.model;



public class ReportItem{
	
	private String 	nutrientTitle;
	private int 	currentIntakeValue;
	private int 	nutriTargetValue;
	
	public ReportItem(){}	
	
	public ReportItem(String title, int intakeVal, int targetVal ){
		this.nutrientTitle = title;
		this.currentIntakeValue = intakeVal;
		this.nutriTargetValue = targetVal;
	}

	public String getNutrientTitle() {
		return nutrientTitle;
	}

	public int getCurrentIntakeValue() {
		return currentIntakeValue;
	}

	public int getNutriTargetValue() {
		return nutriTargetValue;
	}

	public void setNutrientTitle(String nutrientTitle) {
		this.nutrientTitle = nutrientTitle;
	}

	public void setCurrentIntakeVal(int currentIntakeVal) {
		this.currentIntakeValue = currentIntakeVal;
	}

	public void setNutriTargetValue(int nutriTargetValue) {
		this.nutriTargetValue = nutriTargetValue;
	}

	@Override
	public String toString() {
		return "ReportItem [nutrientTitle=" + nutrientTitle
				+ ", currentIntakeValue=" + currentIntakeValue
				+ ", nutriTargetValue=" + nutriTargetValue + "]";
	}
	
}