package com.nutrition.intuition;


import java.util.ArrayList;

import com.nutrition.intuition.adapter.NavDrawerListAdapter;
import com.nutrition.intuition.barscan.IntentIntegrator;
import com.nutrition.intuition.barscan.IntentResult;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.NavDrawerItem;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TestActivity extends Activity {
	private DatabaseManager db;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		
		Log.v("DEBUG", "TEST ACTIVITY TO DO PASS IN FOOD OBJECT " );

		
		/*Intent intent = getIntent();
		Bundle bundle =   intent.getExtras();
		String s = (String) bundle.get("Code"); // Get Scan number..
		// no need to convert?
		long number = Long.parseLong(s);
		Log.v("DEBUG", "SCAN NUMBER IS " + s);
		
		
		db = new DatabaseManager(this);
		if(db.searchEAN(s)){
			Log.v("DEBUG", "SCAN NUMBER IS FOUND !! :@__)))" + number);
			Log.v("DEBUG", "Food item data is as follows" );

			Food food = new Food();
			food = db.selectFood(s);
			
			Log.v("DEBUG", "Food " + food.toString() );
			
			
		}else{
			Log.v("DEBUG", "not here maate");
			// TODO Display toast and call main activity..
			// TODO notfound: dialog: would you like to manual enter this product? Y?N
		}*/
		
		// renderData();


	}


}
