package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nutrition.intuition.adapter.NutriCartReportAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.monitor.NutriCartMonitor;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class NutriCartReport extends ListActivity {
	
	private ArrayList<Food> 			cartList 		= new ArrayList<Food>();	// tracking basket data
	private ArrayList<Food>				foodList		= new ArrayList<Food>();	// complete food object used in calculations and data output.
	private ArrayList<String> 			nutriNamesList 	= new ArrayList<String>();
	private HashMap<String, Integer> 	nutriTotals		= new HashMap<String, Integer>();


	private DatabaseManager 	db;
	private NutriCartMonitor    cartMonitor;
	
	//header
	// nutri name amount visual
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nutri_cart_report);
		
		
		db = new DatabaseManager(this);
	    getActionBar().setHomeButtonEnabled(true);

		Intent intent = getIntent();
		
		// get food onjects passed from last activity.
		cartList = (ArrayList<Food>) intent.getSerializableExtra("data");

		
		// Data is the food names and serving sizes in the nutriCart shopping list.
		// total each of these up and add to the <nutriName, total> map.
		String [] nutriNames = getResources().getStringArray(R.array.nutrient_names);
		// make nutriNames a string list for data operations.
		for(int i = 0; i < nutriNames.length; i++){
			nutriNamesList.add(nutriNames[i]);
		}	
		
		// Make a new cart monitor with the string array data?
		cartMonitor = new NutriCartMonitor(nutriNames);

//		for each of the food objects passed in, output its data
		for(int i = 0; i < cartList.size(); i++){
			Log.v("DEBUG NUTRI", "CART ITEMS ARE (to String) " + cartList.get(i).toString());

		}
		
		// For each food in the cartList, search for matches
		// in the database relating to <FoodName, Serving Size>
		// build a full data model for this match
		// and add the food object to foodList
		Food food;
		for(int i = 0; i < cartList.size(); i++){
			food = new Food();
			
			Log.v("DEBUG", "QUERY DATABASE WITH " + cartList.get(i).getName() + " & " + cartList.get(i).getServingSize());
			
			food = db.selectFood(cartList.get(i).getName().toLowerCase(), cartList.get(i).getServingSize());
			
			Log.v("DEBUG", "Food added to list" + food.getName());
			foodList.add(food);	
		}
				
		// calculate the totals for all the foods in the foodList.
		nutriTotals = cartMonitor.calculateCart(foodList); 
		
		for(int i = 0; i < nutriTotals.size(); i++){
			Log.v("DEBUG NUTRI", "Values after calculation " + nutriTotals.get(nutriNames[i]).intValue());
		}
		
/*		Log.v("DEBUG", "We here");
		for(int i = 0; i < foodList.size(); i++){
			Log.v("DEBUG", "The code is" + cartList.get(i));
		}*/
	
		setFonts();
		
		// search for all foods passed in from cart
		
		
		NutriCartReportAdapter adapter = new NutriCartReportAdapter(this, R.layout.list_item_nutri_cart_report, nutriTotals, nutriNamesList);
		setListAdapter(adapter);
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
		            
		        default:
		            return super.onOptionsItemSelected(item);
	        }
	    }
	
	public void setFonts(){
		// Set font
		TextView tvHeader = (TextView) findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    tvHeader.setTypeface(tf); 
	    
		
	}

}