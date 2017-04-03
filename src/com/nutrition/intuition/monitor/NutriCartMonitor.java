package com.nutrition.intuition.monitor;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.UserProfile;
import com.nutrition.intuition.persistence.DatabaseManager;



public class NutriCartMonitor{
	
	private ArrayList<Food> foods;
	private HashMap<String, Integer> nutriTotals;
	private String[] nutriTitles;
	
	private DatabaseManager db;


	public NutriCartMonitor(String[] nutriTitles){
		this.nutriTitles = nutriTitles;
	}
		
	
	// Pass in foods and serving sizes in args
	// search db for food - store each in a list
	// total this list up and put the totals in a
	// MAP <NUTRINAME, TOTAL>
	public HashMap<String, Integer> calculateCart(ArrayList<Food> foods){
		
		
		// nutriTotals hashmap keeps track of each nutrient total.
		// init map with each nutrient name <String> and value, set to 0.
		nutriTotals = new HashMap<String, Integer>(); // tracks totals
		for(int z = 0; z < nutriTitles.length; z++){
			nutriTotals.put(nutriTitles[z], 0);
		}
	
			// for each foods in the food List.
			for(int i =0; i < foods.size(); i++){
				
				int currentTotal	= 0;	// track user intake value total.
				int nutriVal 		= 0; 	// the food items nutrient value instance.
				
				// get food from foods list
				Food f = foods.get(i);
				// for every nutrient variable in a food object (identical to nutrient at position nutriTitles[i])
				
				// iterate each of the nutrient total instances
				// find a nutrient value of a food object
				// and add it to the current total of the nutrient tracking
				for(int j = 0; j < nutriTotals.size(); j++){
					
					// get current total
					// add new number to total
					// add new total to nutriTotals.
					
					// get the nutrient value of nutrient at nutriTitles[j] from the food object
					
					// get the value of x nutrient from food object
					nutriVal = f.nutriMap.get(nutriTitles[j]); // get the value of nutrient from foods nutriMap.
					Log.v("DEBUG NUTRI", " 1. Nutrient values of " + nutriTitles[j] + " in food is: "  + nutriVal);
					
					//get the same nutrient value x, from the current totals list
					currentTotal = nutriTotals.get(nutriTitles[j]);
					Log.v("DEBUG NUTRI", "2. Current Total for " + nutriTitles[j] + " is " + currentTotal);
					
					// add the totals up.
					currentTotal = currentTotal + nutriVal; //DEBUG
					Log.v("DEBUG NUTRI", "3. New total after current + nutriVal is  "  + currentTotal);

					nutriTotals.put(nutriTitles[j], currentTotal); // add new total to list.
							
				}

			}



			
			return nutriTotals;
		}
}