package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nutrition.intuition.adapter.FoodListAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class FoodListActivity extends ListActivity implements OnItemClickListener  {
	
	private ArrayList<Food> list;
	private DatabaseManager db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_food_list);
		
		// Set font
		TextView tvHeader = (TextView) findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/QuaverSans.otf");
	    tvHeader.setTypeface(tf);
	    
	    getActionBar().setHomeButtonEnabled(true);
	
		Intent intent			= getIntent();
		String catType			= intent.getStringExtra("Cat");
		
		ImageView icon = (ImageView) findViewById(R.id.image);	
		TextView tv = (TextView) findViewById(R.id.head);
		tv.setText(catType);
		
		if(catType.equals("Fruit and Vegatables")){
			icon.setImageResource(R.drawable.fruit_veg);
		}else if(catType.equals("Cereals and Grains")){
			icon.setImageResource(R.drawable.grains);
		}else if(catType.equals("Meat and Fish")){
			icon.setImageResource(R.drawable.meat_seafood);
		}else if(catType.equals("Legumes")){
			icon.setImageResource(R.drawable.legumes);
		}else if(catType.equals("Dairy")){
			icon.setImageResource(R.drawable.dairy);
		}else if(catType.equals("Other")){
			icon.setImageResource(R.drawable.other);
		}

				
		db = new DatabaseManager(this);
		list = db.selectFoodCat(catType, MainActivity.currentUser);
			
		ListView lv = getListView();
		lv.setOnItemClickListener(this);
		
		FoodListAdapter adapter = new FoodListAdapter(this, R.layout.list_food_item, list );
		setListAdapter(adapter);
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
        case android.R.id.home:
        	
        	Log.v("DEBUG", "ICON PRESSED");
        	intent = new Intent(this, MainActivity.class);

        	startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Log.v("DEBUG", "Food item clicked is ");
		
		//String foodName = (String) parent.getItemAtPosition(position);
		Food fd = (Food) parent.getItemAtPosition(position);

		Log.v("DEBUG", "Food item clicked is " + fd.toString());

        // Needed to establish the previous calling activity 
        // (in this case FoodListActivity). This Variable
        // will then be used to determine FoodNutrtionActivity action bar layout
		// for Nutrition facts.
       
		FoodNutritionFactsActivity.callingActivity="FoodList";
        
		Intent intent = new Intent (this, FoodNutritionFactsActivity.class);
		
		Food f = new Food();
		f.setName(list.get(position).getName());
		f.setServingSize(list.get(position).getServingSize());

		Log.v("DEBUG", "Food item clicked is " + f.toString());
		
		// returns food object with complete data set.
		Food food = db.selectFood(f.getName(), f.getServingSize());
		
		Log.v("DEBUG", "Food item Found and to be passed is " + food.toString());

		
		// TODO change to search by name and serving size.
		// TODO update the foodlist activity to show serving size.

		intent.putExtra("Food", food );
		startActivity(intent);	
	}
}
