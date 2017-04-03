package com.nutrition.intuition.model;

import java.io.Serializable;
import java.util.HashMap;

import com.nutrition.intuition.MainActivity;
import com.nutrition.intuition.R;

import android.widget.EditText;

public class Food implements Serializable{
	
	private String[] nutriTitles;
	public HashMap<String, Integer> nutriMap = new HashMap<String, Integer>();
	String name, category;
	String ean;
	int servingSize, calories, totalFat, satFat, transFat, cholesteral, sodium, totalCarbs,
	dietaryFibre, sugars, protein, calcium, iron, vitA, vitB, vitC, vitD, vitE, vitK, 
	potassium, phosphorus, thaimin, riboflavin, niacin, magnesium, manganese, zinc, copper;
	String activityTag;
	
	
	
	public Food(){
		
		nutriTitles = new String[]{"Calories","Total Fat", "Saturated Fat", "Trans Fat", "Cholesteral", "Sodium", "Total Carbohydrates",
						"Dietary Fibre", "Sugars", "Protein", "Calcium", "Iron", "Vitamin A", "Vitamin B", "Vitamin C",
						"Vitamin D", "Vitamin E", "Vitamin K", "Potassium", "Phosphorus", "Thaimin", "RiboFlavin",
						"Niacin", "Magnesium", "Manganese", "Zinc", "Copper"};

		for(int i = 0; i < nutriTitles.length; i++){
			nutriMap.put(nutriTitles[i], 0);
		}
	}


	
	public Food(String name, String category, String ean, int servingSize, int calories, int totalFat, int satFat,
				int transFat, int cholesteral, int sodium, int totalCarbs, int dietaryFibre, int sugars,
				int protein, int calcium, int iron, int vitA, int vitB, int vitC, int vitD, int vitE, int vitK,
				int potassium, int phosphorus, int thaimin, int riboflavin, int niacin, int magnesium, int manganese,
				int zinc, int copper){
		this.name = name;
		this.category = category;
		this.ean = ean;
		this.servingSize = servingSize;
		this.calories = calories;
		this.totalFat = totalFat;
		this.satFat = satFat;
		this.transFat = transFat;
		this.cholesteral = cholesteral;
		this.sodium = sodium;
		this.totalCarbs = totalCarbs;
		this.dietaryFibre = dietaryFibre;
		this.sugars = sugars;
		this.protein = protein;
		this.calcium = calcium;
		this.iron = iron;
		this.vitA = vitA;
		this.vitB = vitB;
		this.vitC = vitC;
		this.vitD = vitD;
		this.vitE = vitE;
		this.vitK = vitK;
		this.potassium = potassium;
		this.phosphorus = phosphorus;
		this.thaimin = thaimin;
		this.riboflavin = riboflavin;
		this.niacin = niacin;
		this.magnesium = magnesium;
		this.manganese = manganese;
		this.zinc = zinc;
		this.copper = copper;
	}
	
	public HashMap<String, Integer> getNutrimap(){
		return nutriMap;
	}

	@Override
	public String toString() {
		return "Food [name=" + name + ", category=" + category + ", ean=" + ean
				+ ", servingSize=" + servingSize + ", calories=" + calories
				+ ", totalFat=" + totalFat + ", satFat=" + satFat
				+ ", transFat=" + transFat + ", cholesteral=" + cholesteral
				+ ", sodium=" + sodium + ", totalCarbs=" + totalCarbs
				+ ", dietaryFibre=" + dietaryFibre + ", sugars=" + sugars
				+ ", protein=" + protein + ", calcium=" + calcium + ", iron="
				+ iron + ", vitA=" + vitA + ", vitB=" + vitB + ", vitC=" + vitC
				+ ", vitD=" + vitD + ", vitE=" + vitE + ", vitK=" + vitK
				+ ", potassium=" + potassium + ", phosphorus=" + phosphorus
				+ ", thaimin=" + thaimin + ", riboflavin=" + riboflavin
				+ ", niacin=" + niacin + ", magnesium=" + magnesium
				+ ", manganese=" + manganese + ", zinc=" + zinc + ", copper="
				+ copper + "]";
	}



	public String getName() {
		return name;
	}



	public String getCategory() {
		return category;
	}



	public String getEan() {
		return ean;
	}



	public int getServingSize() {
		return servingSize;
	}



	public int getCalories() {
		return calories;
	}



	public int getTotalFat() {
		return totalFat;
	}



	public int getSatFat() {
		return satFat;
	}



	public int getTransFat() {
		return transFat;
	}



	public int getCholesteral() {
		return cholesteral;
	}



	public int getSodium() {
		return sodium;
	}



	public int getTotalCarbs() {
		return totalCarbs;
	}



	public int getDietaryFibre() {
		return dietaryFibre;
	}



	public int getSugars() {
		return sugars;
	}



	public int getProtein() {
		return protein;
	}



	public int getCalcium() {
		return calcium;
	}



	public int getIron() {
		return iron;
	}



	public int getVitA() {
		return vitA;
	}



	public int getVitB() {
		return vitB;
	}



	public int getVitC() {
		return vitC;
	}



	public int getVitD() {
		return vitD;
	}



	public int getVitE() {
		return vitE;
	}



	public int getVitK() {
		return vitK;
	}



	public int getPotassium() {
		return potassium;
	}



	public int getPhosphorus() {
		return phosphorus;
	}



	public int getThaimin() {
		return thaimin;
	}



	public int getRiboflavin() {
		return riboflavin;
	}



	public int getNiacin() {
		return niacin;
	}



	public int getMagnesium() {
		return magnesium;
	}



	public int getManganese() {
		return manganese;
	}



	public int getZinc() {
		return zinc;
	}



	public int getCopper() {
		return copper;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public void setEan(String ean) {
		this.ean = ean;
	}



	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}



	public void setCalories(int calories) {
		nutriMap.put("Calories", calories);
		this.calories = calories;
	}



	public void setTotalFat(int totalFat) {
		nutriMap.put("Total Fat", totalFat);
		this.totalFat = totalFat;
	}



	public void setSatFat(int satFat) {
		nutriMap.put("Saturated Fat", satFat);
		this.satFat = satFat;
	}



	public void setTransFat(int transFat) {
		nutriMap.put("Trans Fat", transFat);
		this.transFat = transFat;
	}



	public void setCholesteral(int cholesteral) {
		nutriMap.put("Cholesteral", cholesteral);
		this.cholesteral = cholesteral;
	}



	public void setSodium(int sodium) {
		nutriMap.put("Sodium", sodium);
		this.sodium = sodium;
	}



	public void setTotalCarbs(int totalCarbs) {
		nutriMap.put("Total Carbs", totalCarbs);
		this.totalCarbs = totalCarbs;
	}



	public void setDietaryFibre(int dietaryFibre) {
		nutriMap.put("Dietary Fibre", dietaryFibre);
		this.dietaryFibre = dietaryFibre;
	}



	public void setSugars(int sugars) {
		nutriMap.put("Sugars", sugars);
		this.sugars = sugars;
	}



	public void setProtein(int protein) {
		nutriMap.put("Protein", protein);
		this.protein = protein;
	}



	public void setCalcium(int calcium) {
		nutriMap.put("Calcium", calcium);
		this.calcium = calcium;
	}



	public void setIron(int iron) {
		nutriMap.put("Iron", iron);
		this.iron = iron;
	}



	public void setVitA(int vitA) {
		nutriMap.put("Vitamin A", vitA);
		this.vitA = vitA;
	}



	public void setVitB(int vitB) {
		nutriMap.put("Vitamin B", vitB);
		this.vitB = vitB;
	}



	public void setVitC(int vitC) {
		nutriMap.put("Vitamin c", vitC);
		this.vitC = vitC;
	}



	public void setVitD(int vitD) {
		nutriMap.put("Vitamin D", vitD);

		this.vitD = vitD;
	}



	public void setVitE(int vitE) {
		nutriMap.put("Vitamin E", vitE);

		this.vitE = vitE;
	}



	public void setVitK(int vitK) {
		nutriMap.put("Vitamin K", vitK);

		this.vitK = vitK;
	}



	public void setPotassium(int potassium) {
		nutriMap.put("Potassium", potassium);
		this.potassium = potassium;
	}



	public void setPhosphorus(int phosphorus) {
		nutriMap.put("Phosphorus", phosphorus);
		this.phosphorus = phosphorus;
	}



	public void setThaimin(int thaimin) {
		nutriMap.put("Thaimin", thaimin);
		this.thaimin = thaimin;
	}



	public void setRiboflavin(int riboflavin) {
		nutriMap.put("RiboFlavin", riboflavin);
		this.riboflavin = riboflavin;
	}



	public void setNiacin(int niacin) {
		nutriMap.put("Niacin", niacin);
		this.niacin = niacin;
	}



	public void setMagnesium(int magnesium) {
		nutriMap.put("Magnesium", magnesium);
		this.magnesium = magnesium;
	}



	public void setManganese(int manganese) {
		nutriMap.put("Manganese", manganese);
		this.manganese = manganese;
	}



	public void setZinc(int zinc) {
		nutriMap.put("Zinc", zinc);
		this.zinc = zinc;
	}
	
	public void setCopper(int copper) {
		nutriMap.put("Copper", copper);
		this.copper = copper;
	}
	
	public void setActivityTag(String activityTag) {
		this.activityTag = activityTag;
	}
	
	public String getActivityTag() {
		return activityTag;
	}
}