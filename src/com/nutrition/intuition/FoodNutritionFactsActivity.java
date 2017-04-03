package com.nutrition.intuition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.nutrition.intuition.adapter.NutritionFactsAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.NutritionFactItem;
import com.nutrition.intuition.persistence.DatabaseManager;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.os.Build;

public class FoodNutritionFactsActivity extends ListActivity {
	
	
	private TextView name, ean, sSize, cat;
	private TextView calories, totalFat, satFat, transFat, cholesteral, sodium, totalCarbs,
	dietaryFibre, sugars, protein, calcium, iron, vitA, vitB, vitC, vitD, vitE, vitK, 
	potassium, phosphorus, thaimin, riboflavin, niacin, magnesium, manganese, zinc, copper;
	
	
	
	
	private Food food;
	String sName, category;
	String sEan;// TODO option to hide or view
	int 	serving;
	public static String callingActivity;
	
	private String[] 						nutriTitles;
	private ArrayList<NutritionFactItem> 	nutriFacts = new ArrayList<NutritionFactItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_nutrition_facts);
		
		getActionBar().setDisplayShowTitleEnabled(false);
	    getActionBar().setHomeButtonEnabled(true);

		Log.v("DEBUG", "Calling activity is " + callingActivity);

		nutriTitles 		= getResources().getStringArray(R.array.nutrient_names);
		Intent intent		= getIntent();
		food 				= (Food) intent.getSerializableExtra("Food");

		
		if(food != null){
			 Log.v("DEBUG", "NUTRITION FACTS food " + food.toString());
			/*  TODO 
			 * 
			 * 	Serving Size editability
			 * 
			 */
			 // Everytime a food Item is extracted
			 // it is passed as a serializable object to the food facts activity.
			 // we must consider the use of the adapter in order for the data to be presented to the user correctly.
			 // Given the nature of lists, we must map the data in an iterative process.
			 // For each time the adapter recreates a new row of data, it must use the
			 // data encapsulated in the nutriFacts data structure.
			 // The nutri facts contains data pertaining to each nutrient found in the food.
			 // if no data is available for this fact: stop it from being created in the getview() of the adapter.
			 
			setupNutrientFacts(); // create list of nutrient facts items

			name  = 		(TextView) findViewById(R.id.foodName);
			sSize = 		(TextView) findViewById(R.id.servingSize);
			cat	  =			(TextView) findViewById(R.id.category);
						
			name.setText(food.getName());
			sSize.setText("Serving Size " + String.valueOf(food.getServingSize()));
			cat.setText("Category " + food.getCategory());
			
			Log.v("DEBUG", "ADapting data to list");
			NutritionFactsAdapter adapter = new NutritionFactsAdapter(this, R.layout.list_item_nutrition_facts, nutriFacts );
			setFonts();
			setListAdapter(adapter);
		}else{
			setUpTestFacts();
			NutritionFactsAdapter adapter = new NutritionFactsAdapter(this, R.layout.list_item_nutrition_facts, nutriFacts );
			setFonts();
			setListAdapter(adapter);
		}
			
		
		

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        
        if(callingActivity == "Journal"){
            inflater.inflate(R.menu.food_facts_actions_for_journal, menu);
        }else if (callingActivity == "FoodList"){
            inflater.inflate(R.menu.food_facts_actions, menu);
        }else if(callingActivity == "BarScan"){
        	inflater.inflate(R.menu.food_facts_post_scan, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    
    
    
    
	// Builds a list of NutritionFactItem need for the NutritionFactsAdapter
	public void setupNutrientFacts(){
		 sName = food.getName();
		 sEan = 	food.getEan();// TODO option to hide or view
		 serving =	food.getServingSize();
		 nutriTitles = getResources().getStringArray(R.array.nutrient_names);
		
		 NutritionFactItem fact;
		 for(String title: nutriTitles){
				fact = new NutritionFactItem();
				Log.v("DEBUG", "Creating new Fact for " + title);
				fact.setNutrientTitle(title);
				fact.setNutrientValue(food.nutriMap.get(title));
				Log.v("DEBUG", "New Fact value " + food.nutriMap.get(title));
				fact.setDailyPercentageValue(0);
				nutriFacts.add(fact);
				Log.v("DEBUG", "New Fact added" + title);
			}
	}
	

	
	public void setUpTestFacts(){
		
		for(int i = 0; i < nutriTitles.length; i++){
			NutritionFactItem n = new NutritionFactItem(nutriTitles[i], 666, 666);
			nutriFacts.add(n);
		}
		
	}
	
	// Action bar options: Add to food Journal or add to nutri Cart
	// check if item is already existing in either table before
	// entry.
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	DatabaseManager db;
    	String date = calculateDate(0);
    	
        // Handle item selection
        switch (item.getItemId()) {
        
        case android.R.id.home:
        	intent = new Intent(this, MainActivity.class);
        	startActivity(intent);
            return true;
        
        case R.id.add_to_journal:
        	// Add to Journal        	
        	db = new DatabaseManager(this);
        	intent = new Intent(this, MainActivity.class);        	
        	String s = getTimestamp();
        	db.addFoodToJournal(food, date, getTimestamp(), MainActivity.currentUser);
        	Toast.makeText(this, sName + " added to your Food Journal", Toast.LENGTH_SHORT).show();
        	startActivity(intent);
            return true;
            
        case R.id.add_to_cart:
        	
        	db = new DatabaseManager(this);
        	db.addToNutriCart(food.getName(), food.getServingSize(), MainActivity.currentUser);
        	intent = new Intent(this, MainActivity.class);
        	Toast.makeText(this, sName + " added to your nutriCart", Toast.LENGTH_SHORT).show();        	        	
        	//intent.putStringArrayListExtra("data", (ArrayList<String>) basket);
        	startActivity(intent);
            return true;
            
        case R.id.add_to_library:
        	db = new DatabaseManager(this);
        	
        	// build food object
        	// add to food_table.
        	Log.v("DEBUG", "FOOD Data to be inserted to library.." + food.toString());
        	
        	// TODO: ADd food data to FOOD_TABLE (user foods)
        	
        	db.addNewFoodItem(food, MainActivity.currentUser);
        	intent = new Intent(this, MainActivity.class);
        	Toast.makeText(this, sName + " added to your Library", Toast.LENGTH_SHORT).show();        	        	
        	startActivity(intent);
        	
        	/*db.addToFoodLibrary(food.getName(), food.getServingSize());
        	intent = new Intent(this, MainActivity.class);
        	Toast.makeText(this, sName + " added to your nutriCart", Toast.LENGTH_SHORT).show();        	        	
        	//intent.putStringArrayListExtra("data", (ArrayList<String>) basket);
        	*/
        	return true;
            
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	public String getTimestamp(){
        long millis = new Date().getTime();
        String time = SimpleDateFormat
                       .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.UK)
                       .format(millis);    	
        
        String stime = time.substring(0, Math.min(time.length(), 5));
		return stime;
	}
	public String calculateDate(int calibrate){	
		// Construct todays calendar date.
		Calendar cal = Calendar.getInstance();
		int day 	= cal.get(Calendar.DAY_OF_MONTH);
		int dayB 	= cal.get(Calendar.DAY_OF_WEEK);
		int month 	= cal.get(Calendar.MONTH);
		int year 	= cal.get(Calendar.YEAR);
		
		if(calibrate > 0){
			day = day-calibrate;
		}
		//Calendar.Month is 0 indexed, so increment + 1 to calibrate to Human logic. 
		month =+ 1 ;
		// Output Test
		Log.v("JOURNAL Time Test", "Day a: " + day);
		Log.v("JOURNAL Time Test", "Day b: " + dayB);
		Log.v("JOURNAL Time Test", "Month: " + month);
		Log.v("JOURNAL Time Test", "Year: " + year);
		// Construct string in final formatting of DD-MM-YYYY
		String sDate = day + "-" + month + "-" + year;
		return sDate;
	}
	
	// ToDo return boolean.
	public void setFonts(){
		// ToDo return boolean.
			// Set font
			TextView tvHeader = (TextView) findViewById(R.id.head);
			Typeface tf = Typeface.createFromAsset(getAssets(),
		            "fonts/QuaverSans.otf");	
					//"fonts/LeagueGothicRegular.otf");
		    tvHeader.setTypeface(tf); 
		    
			// Set font Diet
			TextView tvFoodName = (TextView) findViewById(R.id.foodName);
			tf = Typeface.createFromAsset(getAssets(),
		            "fonts/QuaverSans.otf");
		    tvFoodName.setTypeface(tf); 
			// Set font Diet
			TextView tvServingSize = (TextView) findViewById(R.id.servingSize);
			tf = Typeface.createFromAsset(getAssets(),
		            "fonts/QuaverSans.otf");
			tvServingSize.setTypeface(tf); 
			TextView tvCat = (TextView) findViewById(R.id.category);
			tf = Typeface.createFromAsset(getAssets(),
		            "fonts/QuaverSans.otf");
			tvCat.setTypeface(tf); 
		
	}

}
