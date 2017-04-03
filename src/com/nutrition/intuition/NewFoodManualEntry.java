package com.nutrition.intuition;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nutrition.intuition.adapter.NewFoodNutrientAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class NewFoodManualEntry extends Activity {

	private String[] 					nutriTitles; // Nutrient Name resource
	private ListView mDrawerList;
	private NewFoodNutrientAdapter adapter;
	
	private DatabaseManager db;

	
	private Food 	food;
	private Spinner spinnerCat ;
	private String 	foodName, eanNo, category;
	private int  	servingSize;
	private HashMap<String, Integer>	nutriValues = new HashMap<String, Integer>();
	private EditText name, ean, sSize, cat;
	private EditText calories, totalFat, satFat, transFat, cholesteral, sodium, totalCarbs,
	dietaryFibre, sugars, protein, calcium, iron, vitA, vitB, vitC, vitD, vitE, vitK, 
	potassium, phosphorus, thaimin, riboflavin, niacin, magnesium, manganese, zinc, copper;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_food_manual_entry);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		mapWidgets();
		setFonts();
		
	    getActionBar().setHomeButtonEnabled(true);

	    nutriTitles = getResources().getStringArray(R.array.nutrient_names); 
		adapter = new NewFoodNutrientAdapter(this, R.layout.list_item_new_food, nutriTitles );
	}
	
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_food_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
        
        case android.R.id.home:
        	intent = new Intent(this, MainActivity.class);
        	startActivity(intent);
            return true;
        
        
        case R.id.add_to_database:
        	
        	if(validate() == false){
				  validate();
			  }else{
				
				  // Manual FOOD DATA ENTRY is done on 2 levels:
				  // First: check if food exists in Users Food_TABLE
				  // 		If not, add it. Else inform user of no duplicate entries.
				  // Second: Once a food is entered to the user Food_Table
				  // 		 we check if it exists in the SYSTEM_FOOD_TABLE
				  // 		This table is used for barcode data reference with preloaded data
				  //		If not entry exists, add it to SYSTEM_TABLE.
				  //		else, pass on.
				  
				  
				 // 1. Get a Food Object with complete nutrient Values set
				 // 2. Check if this food is already in the database Food_Table 
				 // 3. If Found, inform end user.
				 // 4. Else, Add new Food item to FOOD_TABLE.
				 getValues();
	        	// Begin database operation
	        	db = new DatabaseManager(this);
	        	
	        	// check for duplicate entrys in the UserLibrary table based on a <Name, ServingSize> basis
	        	if(db.checkForDuplicates(food.getName().toLowerCase(), food.getServingSize())){
	            	Toast.makeText(this," This entry is already found.\n" +  "Try another Name Size combination", Toast.LENGTH_SHORT).show();
	        	}else{
	        		//  no duplicate entry found, so go ahead and add the food to the library
		        	db.addNewFoodItem(food, MainActivity.currentUser); // add to the user food library
		        	// check if the food is already logged in the system table.
		        	if(db.checkForSystemTableDuplicates(food.getName().toLowerCase(), food.getServingSize())){
		        		Log.v("DEBUG SYSTEM", "Item already found on system table, not added.");
		        	}else{
			        	db.addToSystemTable(food);// logs food into the system table for future processes.
		        		Log.v("DEBUG SYSTEM", "Not found On system table, now Logging . . .");

		        	}
		        	intent = new Intent(this, MainActivity.class);
		        	startActivity(intent);
	        	}
			  }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
	
    public void getValues(){
    	
    	// Add keys to map.
    	for(String title: nutriTitles){
    		nutriValues.put(title, 0);
    	} 
	     
	     // map user data to new food data model.
	     foodName 			=   name.getText().toString().toLowerCase();
	     eanNo 				=   ean.getText().toString();
	     category			=   (String) spinnerCat.getSelectedItem();
	     servingSize		=   Integer.parseInt(sSize.getText().toString());
	     
	     // TODO handle no entries..
	     nutriValues.put("Calories", Integer.parseInt(calories.getText().toString()));
	     nutriValues.put("Total Fat", Integer.parseInt(totalFat.getText().toString()));
	     nutriValues.put("Saturated Fat", Integer.parseInt(satFat.getText().toString()));
	     nutriValues.put("Trans Fat", Integer.parseInt(transFat.getText().toString()));
	     nutriValues.put("Cholesteral", Integer.parseInt(cholesteral.getText().toString()));
	     nutriValues.put("Sodium", Integer.parseInt(sodium.getText().toString()));
	     nutriValues.put("Total Carbohydrates", Integer.parseInt(totalCarbs.getText().toString()));
	     nutriValues.put("Dietary Fibre", Integer.parseInt(dietaryFibre.getText().toString()));
	     nutriValues.put("Sugars", Integer.parseInt(sugars.getText().toString()));
	     nutriValues.put("Protein", Integer.parseInt(protein.getText().toString()));
	     nutriValues.put("Calcium", Integer.parseInt(calcium.getText().toString()));
	     nutriValues.put("Iron", Integer.parseInt(iron.getText().toString()));
	     nutriValues.put("Vitamin A", Integer.parseInt(vitA.getText().toString()));
	     nutriValues.put("Vitamin B", Integer.parseInt(vitB.getText().toString()));
	     nutriValues.put("Vitamin C", Integer.parseInt(vitC.getText().toString()));
	     nutriValues.put("Vitamin D", Integer.parseInt(vitD.getText().toString()));
	     nutriValues.put("Vitamin E", Integer.parseInt(vitE.getText().toString()));
	     nutriValues.put("Vitamin K", Integer.parseInt(vitK.getText().toString()));
	     nutriValues.put("Potassium", Integer.parseInt(potassium.getText().toString()));
	     nutriValues.put("Phosphorus", Integer.parseInt(phosphorus.getText().toString()));
	     nutriValues.put("Thaimin", Integer.parseInt(thaimin.getText().toString()));
	     nutriValues.put("Riboflavin", Integer.parseInt(riboflavin.getText().toString())); 
	     nutriValues.put("Niacin", Integer.parseInt(niacin.getText().toString()));
	     nutriValues.put("Magnesium", Integer.parseInt(magnesium.getText().toString()));
	     nutriValues.put("Manganese", Integer.parseInt(manganese.getText().toString()));
	     nutriValues.put("Zinc", Integer.parseInt(zinc.getText().toString()));
	     nutriValues.put("Copper", Integer.parseInt(copper.getText().toString()));
	
	     food = new Food();
	     food.setName(foodName);
	     food.setEan(eanNo);
	     food.setCategory(category);
	     Log.v("DEBUG", "Food ITEM CAT IS "+ category);
	     
	     food.setServingSize(servingSize);    
	     food.setCalories(Integer.parseInt(calories.getText().toString()));
	     food.setTotalFat(Integer.parseInt(totalFat.getText().toString()));
	     food.setSatFat(Integer.parseInt(satFat.getText().toString()));
	     food.setTransFat(Integer.parseInt(transFat.getText().toString()));
	     food.setCholesteral(Integer.parseInt(cholesteral.getText().toString()));
	     food.setSodium(Integer.parseInt(sodium.getText().toString()));
	     food.setTotalCarbs(Integer.parseInt(totalCarbs.getText().toString()));
	     food.setDietaryFibre(Integer.parseInt(dietaryFibre.getText().toString()));
	     food.setSugars(Integer.parseInt(sugars.getText().toString()));
	     food.setProtein(Integer.parseInt(protein.getText().toString()));
	     food.setCalcium(Integer.parseInt(calcium.getText().toString()));
	     food.setIron(Integer.parseInt(iron.getText().toString()));
	     food.setVitA(Integer.parseInt(vitA.getText().toString()));
	     food.setVitB(Integer.parseInt(vitB.getText().toString()));
	     food.setVitC(Integer.parseInt(vitC.getText().toString()));
	     food.setVitD(Integer.parseInt(vitD.getText().toString()));
	     food.setVitE(Integer.parseInt(vitE.getText().toString()));
	     food.setVitK(Integer.parseInt(vitK.getText().toString()));
	     food.setPotassium(Integer.parseInt(potassium.getText().toString()));
	     food.setPhosphorus(Integer.parseInt(phosphorus.getText().toString()));
	     food.setThaimin(Integer.parseInt(thaimin.getText().toString()));
	     food.setRiboflavin(Integer.parseInt(riboflavin.getText().toString()));
	     food.setNiacin(Integer.parseInt(niacin.getText().toString()));
	     food.setMagnesium(Integer.parseInt(magnesium.getText().toString()));
	     food.setManganese(Integer.parseInt(manganese.getText().toString()));
	     food.setZinc(Integer.parseInt(zinc.getText().toString()));
	     food.setCopper(Integer.parseInt(copper.getText().toString()));
	     
	     Log.v("DEBUG", "FOOD ITEM " + food.toString());
	     

    }
	
	public boolean validate(){			
			int i = 0;
			
			if(name.getText().toString().equals("")){
				Toast.makeText(this, "You must enter a food item name", Toast.LENGTH_SHORT).show();
				i++;
			}/*else if (database.checkForFoodItemDuplicates( foodname.getText().toString(), Integer.parseInt(servingSize.getText().toString()) )){
				Toast.makeText(this, "Food Entry for name and serving amount exists, please enter a different name. No duplicates allowed.", Toast.LENGTH_SHORT).show();  
				i++;
			}*/else if(calories.getText().toString().equals("")){
				Toast.makeText(this, "You did not enter any", Toast.LENGTH_SHORT).show();
				i++;
			}
			else if(sSize.getText().toString().equals("")){
				Toast.makeText(this, "You did not enter a serving size", Toast.LENGTH_SHORT).show();
				i++;
			}else if(spinnerCat.getSelectedItem().toString().equals("")){
				Toast.makeText(this, "You did not enter a category", Toast.LENGTH_SHORT).show();
				i++;
			} if(ean.getText().toString().equals("")){
				ean.setText("0");
			}if(totalFat.getText().toString().equals("")){
				totalFat.setText("0");
			}if(transFat.getText().toString().equals("")){
				transFat.setText("0");
			}if(satFat.getText().toString().equals("")){
				satFat.setText("0");
			}if(cholesteral.getText().toString().equals("")){
				cholesteral.setText("0");
			}if(sodium.getText().toString().equals("")){
				sodium.setText("0");
			}if(totalCarbs.getText().toString().equals("")){
				totalCarbs.setText("0");
			}if(dietaryFibre.getText().toString().equals("")){
				dietaryFibre.setText("0");
			}if(sugars.getText().toString().equals("")){
				sugars.setText("0");
			}if(calcium.getText().toString().equals("")){
				calcium.setText("0");
			}if(protein.getText().toString().equals("")){
				protein.setText("0");
			}if(iron.getText().toString().equals("")){
				iron.setText("0");
			}if(vitA.getText().toString().equals("")){
				vitA.setText("0");
			}if(vitB.getText().toString().equals("")){
				vitB.setText("0");
			}if(vitC.getText().toString().equals("")){
				vitC.setText("0");
			}if(vitD.getText().toString().equals("")){
				vitD.setText("0");
			}if(vitE.getText().toString().equals("")){
				vitE.setText("0");
			}if(vitK.getText().toString().equals("")){
				vitK.setText("0");
			}if(potassium.getText().toString().equals("")){
				potassium.setText("0");
			}if(phosphorus.getText().toString().equals("")){
				phosphorus.setText("0");
			}if(thaimin.getText().toString().equals("")){
				thaimin.setText("0");
			}if(riboflavin.getText().toString().equals("")){
				riboflavin.setText("0");
			}if(niacin.getText().toString().equals("")){
				niacin.setText("0");
			}if(magnesium.getText().toString().equals("")){
				magnesium.setText("0");
			}if(manganese.getText().toString().equals("")){
				manganese.setText("0");
			}if(zinc.getText().toString().equals("")){
				zinc.setText("0");
			}if(copper.getText().toString().equals("")){
				copper.setText("0");
			}
						
			if(i > 0){
				return false;
			}else{
				return true;
			}
	}
	

	public void setFonts(){
		TextView tvHeader = (TextView) findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/QuaverSans.otf");	
	    tvHeader.setTypeface(tf); 
	       
	}
	
	
	public void mapWidgets(){
	     name 	  	    = (EditText) findViewById(R.id.foodName); 
	     ean 		  	= (EditText) findViewById(R.id.eanNumber); 
	     sSize 		  	= (EditText) findViewById(R.id.servingSizeValue); 
	     spinnerCat		= (Spinner) findViewById(R.id.spinner1);
	     calories 	  	= (EditText) findViewById(R.id.et1); 
	     totalFat 	  	= (EditText) findViewById(R.id.et2);
	     satFat 	  	= (EditText) findViewById(R.id.et3);
	     transFat     	= (EditText) findViewById(R.id.et4);
	     cholesteral  	= (EditText) findViewById(R.id.et5);
	     sodium 	  	= (EditText) findViewById(R.id.et6);
	     totalCarbs   	= (EditText) findViewById(R.id.et7);
	     dietaryFibre 	= (EditText) findViewById(R.id.et8);
	     sugars 		= (EditText) findViewById(R.id.et9);
	     protein 		= (EditText) findViewById(R.id.et10);
	     calcium 		= (EditText) findViewById(R.id.et11);
	     iron 			= (EditText) findViewById(R.id.et12);
	     vitA 			= (EditText) findViewById(R.id.et13);
	     vitB 			= (EditText) findViewById(R.id.et14);
	     vitC 			= (EditText) findViewById(R.id.et15);
	     vitD 			= (EditText) findViewById(R.id.et16);
	     vitE 			= (EditText) findViewById(R.id.et17);
	     vitK 			= (EditText) findViewById(R.id.et18);
	     potassium 		= (EditText) findViewById(R.id.et19);
	     phosphorus 	= (EditText) findViewById(R.id.et20);
	     thaimin 		= (EditText) findViewById(R.id.et21);
	     riboflavin		= (EditText) findViewById(R.id.et22);
	     niacin 		= (EditText) findViewById(R.id.et23);
	     magnesium 		= (EditText) findViewById(R.id.et24);
	     manganese 		= (EditText) findViewById(R.id.et25);
	     zinc 			= (EditText) findViewById(R.id.et26);
	     copper 		= (EditText) findViewById(R.id.et27);
	     
			Intent intent			= getIntent();	
			if(intent.hasExtra("EAN")){
				String EAN			    = intent.getStringExtra("EAN");
				Log.v("DEBUG EAN", "EAN is " + EAN );
				ean.setText(EAN);
			}
	}


}
