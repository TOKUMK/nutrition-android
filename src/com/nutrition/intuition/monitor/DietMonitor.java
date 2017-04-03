package com.nutrition.intuition.monitor;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.nutrition.intuition.HomeFragment;
import com.nutrition.intuition.R;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.UserProfile;



/*
 * 	Function of DietMonitor:
 * 
 * 	Consume user profile data and eating habits.
 *  Return a summation of nutritional metrics
 *  
 *  Should perform a "Check-up" on the diet
 *  at set intervals throughout the day.
 *  
 *  
 *  
 */
public class DietMonitor{
	
	private UserProfile profile;
	private ArrayList<Food> foods;
	private String[] nutriTitles;
			
	private HashMap<String, Integer> nutriTotals;
	private HashMap<String, Integer> nutriTargetsGDA;
	
	/* values are initialised at runtime */
	private HashMap<String, Integer> gdaAvgMale;
	private HashMap<String, Integer> gdaAvgFemale;
	
	private HashMap<String, Integer> gdaMassMale;
	private HashMap<String, Integer> gdaMassFemale;
	
	private HashMap<String, Integer> gdaLossMale;
	private HashMap<String, Integer> gdaLossFemale;
	
	/* Exercise estimate needed to calculate BMI */
	protected double LITTLE_TO_NO_EXERCISE		=  	1.2;
	protected double LIGHT_EXERCISE 			= 	1.375;
	protected double MODERATE_EXERCISE 			= 	1.55;
	protected double HEAVY_EXERCISE				= 	1.725;
	protected double VERY_HEAVY_EXERCISE 		= 	1.9; 

	protected double BMR;

	
	
	
	public DietMonitor(UserProfile user, String[] nutriTitles){
		initGDAValues();
		this.profile = user;
		this.nutriTitles = nutriTitles;
		
		if(profile.getGender().equals("Male")){
			if(profile.getDiet().equals("Average Diet")){
				nutriTargetsGDA = gdaAvgMale;
				// check exercise type
				// recalculate calorific intake based on BMI
			}else if(profile.getDiet().equals("Loose Weight")){
				nutriTargetsGDA = gdaLossMale;

			}else if(profile.getDiet().equals("Build Mass")){
				nutriTargetsGDA = gdaMassMale;
			}
		} 
		if(profile.getGender().equals("Female")){
			if(profile.getDiet().equals("Average Diet")){
				nutriTargetsGDA = gdaAvgFemale;
				// check exercise type
				// recalculate calorific intake based on BMI
			}else if(profile.getDiet().equals("Loose Weight")){
				nutriTargetsGDA = gdaLossFemale;

			}else if(profile.getDiet().equals("Build Mass")){
				nutriTargetsGDA = gdaMassFemale;
			}
		}
		
		
		nutriTargetsGDA.put("Calories", (int) calculateBMR());
		
	}

	public HashMap<String, Integer> getNutriTargetsGDA(){
		return nutriTargetsGDA;
	}
	
	public double calculateBMR(){
		double calories=0;
		int weight = profile.getWeight();
		int height = profile.getHeight();
		int age	   = profile.getAge();
		String exercise = profile.getExercise();
		
		if(profile.getGender().equals("Male")){
			BMR = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
		}else if(profile.getGender().equals("Female")){
			BMR = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
		}
		Log.v("DEBUG"," BMR IS " +  BMR);

		
		if(exercise.equals("Little to No Exercise")){
			calories = BMR * LITTLE_TO_NO_EXERCISE;
		}else if(exercise.equals("Light Exercise")){
			calories = BMR * LIGHT_EXERCISE;
		}else if(exercise.equals("Moderate Exercise")){
			calories = BMR * MODERATE_EXERCISE;
		}else if(exercise.equals("Heavy Exercise")){
			calories = BMR * HEAVY_EXERCISE;
		}else if(exercise.equals("Intensive Exercise")){
			calories = BMR * VERY_HEAVY_EXERCISE;
		}
		
		Log.v("DEBUG"," Output of BMR Calories equation is " + calories);

		return calories;
/*		String sCal = Double.toString(calories);
		return Integer.parseInt(sCal);*/
	}
	
	
	
	
	// Each time the food list is passed via args, the nutriTotals
	// is reset, and each nutrients total is recalculated based on
	// the foods List.
	public HashMap<String, Integer> calculateDailyReport(ArrayList<Food> foods){
		// Nutri Totals holds total data regarding nutrient consumption.
		nutriTotals = new HashMap<String, Integer>();
		
		// init all values to 0.
		for(int z = 0; z < nutriTitles.length; z++){
			nutriTotals.put(nutriTitles[z], 0);
		}
		// iterate foods collection
		// for each reference nutrient name in nutriTitles resource array,
		// get a food items corresponding nutrient value
		// we then have the food items data as mapped as
		// <nutrient name, nutrient value>
		for(int i =0; i < foods.size(); i++){
			int nutriVal 	 = 0; 	// foods nutrient value.
			int currentTotal = 0;
			Food f = foods.get(i);
			// for every nutrient variable in a food object (identical to nutriTitles[])
			for(int j = 0; j < nutriTitles.length; j++){
				
				// get the current nutrient total
				// add the selected nutrient value to total
				// add new total to nutriTotals.
				// get the nutrient value of nutrient at nutriTitles[j] from the food object
				nutriVal = f.nutriMap.get(nutriTitles[j]);
				currentTotal = nutriTotals.get(nutriTitles[j]);
				currentTotal = currentTotal + nutriVal;
				nutriTotals.put(nutriTitles[j], currentTotal);
			}
		}
		return nutriTotals;
	}
	
	
	/*the gda values are based on user profile data. 
	*/
	public void initGDAValues(){
		
		nutriTargetsGDA = new HashMap<String, Integer>();
		
		gdaAvgMale = new HashMap<String, Integer>();
		gdaAvgFemale= new HashMap<String, Integer>();
		
		gdaMassMale= new HashMap<String, Integer>();
		gdaMassFemale= new HashMap<String, Integer>();
		
		gdaLossMale= new HashMap<String, Integer>();
		gdaLossFemale= new HashMap<String, Integer>();
				
		 gdaAvgMale.put("Calories", 2500);	
		 gdaAvgMale.put("Total Fat", 95);
		 gdaAvgMale.put("Saturated Fat", 30);
		 gdaAvgMale.put("Trans Fat", 30);
		 gdaAvgMale.put("Cholesteral", 300);
		 gdaAvgMale.put("Sodium", 1500);
		 gdaAvgMale.put("Total Carbohydrates", 300);
		 gdaAvgMale.put("Dietary Fibre", 38);
		 gdaAvgMale.put("Sugars", 120);
		 gdaAvgMale.put("Protein", 55);   
		 gdaAvgMale.put("Calcium", 1);
		 gdaAvgMale.put("Iron", 8);
		 gdaAvgMale.put("Vitamin A", 0);
		 gdaAvgMale.put("Vitamin B", 0);
		 gdaAvgMale.put("Vitamin C", 0);
		 gdaAvgMale.put("Vitamin D", 0);
		 gdaAvgMale.put("Vitamin E", 0);
		 gdaAvgMale.put("Vitamin K", 0);
		 gdaAvgMale.put("Potassium", 0);
		 gdaAvgMale.put("Phosphorus", 0);
		 gdaAvgMale.put("Thaimin", 0);
		 gdaAvgMale.put("RiboFlavin", 0);
		 gdaAvgMale.put("Niacin", 0);
		 gdaAvgMale.put("Magnesium", 0);
		 gdaAvgMale.put("Manganese", 0);
		 gdaAvgMale.put("Zinc", 0);
		 gdaAvgMale.put("Copper", 0);
		 
		 gdaAvgFemale.put("Calories", 2500);	
		 gdaAvgFemale.put("Total Fat", 95);
		 gdaAvgFemale.put("Saturated Fat", 30);
		 gdaAvgFemale.put("Trans Fat", 30);
		 gdaAvgFemale.put("Cholesteral", 300);
		 gdaAvgFemale.put("Sodium", 1500);
		 gdaAvgFemale.put("Total Carbohydrates", 300);
		 gdaAvgFemale.put("Dietary Fibre", 38);
		 gdaAvgFemale.put("Sugars", 120);
		 gdaAvgFemale.put("Protein", 55);   
		 gdaAvgFemale.put("Calcium", 1);
		 gdaAvgFemale.put("Iron", 8);
		 gdaAvgFemale.put("Vitamin A", 0);
		 gdaAvgFemale.put("Vitamin B", 0);
		 gdaAvgFemale.put("Vitamin C", 0);
		 gdaAvgFemale.put("Vitamin D", 0);
		 gdaAvgFemale.put("Vitamin E", 0);
		 gdaAvgFemale.put("Vitamin K", 0);
		 gdaAvgFemale.put("Potassium", 0);
		 gdaAvgFemale.put("Phosphorus", 0);
		 gdaAvgFemale.put("Thaimin", 0);
		 gdaAvgFemale.put("RiboFlavin", 0);
		 gdaAvgFemale.put("Niacin", 0);
		 gdaAvgFemale.put("Magnesium", 0);
		 gdaAvgFemale.put("Manganese", 0);
		 gdaAvgFemale.put("Zinc", 0);
		 gdaAvgFemale.put("Copper", 0);
		 
		  /*
		   *  For building Muscle Mass, a focus on Calorific intake,
		   *  Protein and carbs is important.
		   */  
		 gdaMassMale.put("Calories", 3500);	
		 gdaMassMale.put("Total Fat", 95);
		 gdaMassMale.put("Saturated Fat", 30);
		 gdaMassMale.put("Trans Fat", 30);
		 gdaMassMale.put("Cholesteral", 300);
		 gdaMassMale.put("Sodium", 1500);
		 gdaMassMale.put("Total Carbohydrates", 350);
		 gdaMassMale.put("Dietary Fibre", 38);
		 gdaMassMale.put("Sugars", 120);
		 gdaMassMale.put("Protein", 95);   
		 gdaMassMale.put("Calcium", 1);
		 gdaMassMale.put("Iron", 8);
		 gdaMassMale.put("Vitamin A", 0);
		 gdaMassMale.put("Vitamin B", 0);
		 gdaMassMale.put("Vitamin C", 0);
		 gdaMassMale.put("Vitamin D", 0);
		 gdaMassMale.put("Vitamin E", 0);
		 gdaMassMale.put("Vitamin K", 0);
		 gdaMassMale.put("Potassium", 0);
		 gdaMassMale.put("Phosphorus", 0);
		 gdaMassMale.put("Thaimin", 0);
		 gdaMassMale.put("RiboFlavin", 0);
		 gdaMassMale.put("Niacin", 0);
		 gdaMassMale.put("Magnesium", 0);
		 gdaMassMale.put("Manganese", 0);
		 gdaMassMale.put("Zinc", 0);
		 gdaMassMale.put("Copper", 0);
		 		 
		 gdaMassFemale.put("Calories", 2500);	
		 gdaMassFemale.put("Total Fat", 70);
		 gdaMassFemale.put("Saturated Fat", 20);
		 gdaMassFemale.put("Trans Fat", 30);
		 gdaMassFemale.put("Cholesteral", 300);
		 gdaMassFemale.put("Sodium", 1500);
		 gdaMassFemale.put("Total Carbohydrates", 270);
		 gdaMassFemale.put("Dietary Fibre", 24);
		 gdaMassFemale.put("Sugars", 90);
		 gdaMassFemale.put("Protein", 65);   
		 gdaMassFemale.put("Calcium", 1);
		 gdaMassFemale.put("Iron", 8);
		 gdaMassFemale.put("Vitamin A", 0);
		 gdaMassFemale.put("Vitamin B", 0);
		 gdaMassFemale.put("Vitamin C", 0);
		 gdaMassFemale.put("Vitamin D", 0);
		 gdaMassFemale.put("Vitamin E", 0);
		 gdaMassFemale.put("Vitamin K", 0);
		 gdaMassFemale.put("Potassium", 0);
		 gdaMassFemale.put("Phosphorus", 0);
		 gdaMassFemale.put("Thaimin", 0);
		 gdaMassFemale.put("RiboFlavin", 0);
		 gdaMassFemale.put("Niacin", 0);
		 gdaMassFemale.put("Magnesium", 0);
		 gdaMassFemale.put("Manganese", 0);
		 gdaMassFemale.put("Zinc", 0);
		 gdaMassFemale.put("Copper", 0);
		
		 
		  /*
		   *  For Loosing Fat, a focus on less Calorific intake,
		   *  Protein, Carbs and Fats is important.
		   */ 
		 gdaLossMale.put("Calories", 200);	
		 gdaLossMale.put("Total Fat", 75);
		 gdaLossMale.put("Saturated Fat", 30);
		 gdaLossMale.put("Trans Fat", 30);
		 gdaLossMale.put("Cholesteral", 300);
		 gdaLossMale.put("Sodium", 1500);
		 gdaLossMale.put("Total Carbohydrates", 300);
		 gdaLossMale.put("Dietary Fibre", 38);
		 gdaLossMale.put("Sugars", 120);
		 gdaLossMale.put("Protein", 55);   
		 gdaLossMale.put("Calcium", 1);
		 gdaLossMale.put("Iron", 8);
		 gdaLossMale.put("Vitamin A", 0);
		 gdaLossMale.put("Vitamin B", 0);
		 gdaLossMale.put("Vitamin C", 0);
		 gdaLossMale.put("Vitamin D", 0);
		 gdaLossMale.put("Vitamin E", 0);
		 gdaLossMale.put("Vitamin K", 0);
		 gdaLossMale.put("Potassium", 0);
		 gdaLossMale.put("Phosphorus", 0);
		 gdaLossMale.put("Thaimin", 0);
		 gdaLossMale.put("RiboFlavin", 0);
		 gdaLossMale.put("Niacin", 0);
		 gdaLossMale.put("Magnesium", 0);
		 gdaLossMale.put("Manganese", 0);
		 gdaLossMale.put("Zinc", 0);
		 gdaLossMale.put("Copper", 0);
		
		 gdaLossFemale.put("Calories", 1500);	
		 gdaLossFemale.put("Total Fat", 50);
		 gdaLossFemale.put("Saturated Fat", 20);
		 gdaLossFemale.put("Trans Fat", 30);
		 gdaLossFemale.put("Cholesteral", 300);
		 gdaLossFemale.put("Sodium", 1500);
		 gdaLossFemale.put("Total Carbohydrates", 230);
		 gdaLossFemale.put("Dietary Fibre", 24);
		 gdaLossFemale.put("Sugars", 90);
		 gdaLossFemale.put("Protein", 45);   
		 gdaLossFemale.put("Calcium", 1);
		 gdaLossFemale.put("Iron", 8);
		 gdaLossFemale.put("Vitamin A", 0);
		 gdaLossFemale.put("Vitamin B", 0);
		 gdaLossFemale.put("Vitamin C", 0);
		 gdaLossFemale.put("Vitamin D", 0);
		 gdaLossFemale.put("Vitamin E", 0);
		 gdaLossFemale.put("Vitamin K", 0);
		 gdaLossFemale.put("Potassium", 0);
		 gdaLossFemale.put("Phosphorus", 0);
		 gdaLossFemale.put("Thaimin", 0);
		 gdaLossFemale.put("RiboFlavin", 0);
		 gdaLossFemale.put("Niacin", 0);
		 gdaLossFemale.put("Magnesium", 0);
		 gdaLossFemale.put("Manganese", 0);
		 gdaLossFemale.put("Zinc", 0);
		 gdaLossFemale.put("Copper", 0);
	}
	
}