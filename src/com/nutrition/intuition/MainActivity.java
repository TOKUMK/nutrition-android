package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.nutrition.intuition.network.SyncUserTask;
import com.nutrition.intuition.adapter.NavDrawerListAdapter;
import com.nutrition.intuition.barscan.IntentIntegrator;
import com.nutrition.intuition.barscan.IntentResult;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.NavDrawerItem;
import com.nutrition.intuition.model.UserProfile;
import com.nutrition.intuition.model.UserReport;
import com.nutrition.intuition.monitor.DietMonitor;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;
	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	private NavDrawerListAdapter adapter;
	
	private DatabaseManager db;
	public UserProfile profile;
	private DietMonitor monitor;
	
	private ArrayList<Food> foods;
	public static int cartSize;
	public static int journalSize;
	
	private String scanContent;

	public static String currentUser, callingActivity;
	
    private HashMap<String, Integer> nutrientTotals 	= new HashMap<String, Integer>();
    private UserReport reportData;
    

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		// Load resource of nutrient names
        String[] nutriTitles = getResources().getStringArray(R.array.nutrient_names);        
        db = new DatabaseManager(this);

        // if the last activity was either login or register
        // the assign current User of the system by username at login or reg.
        if(callingActivity.equals("Login") ||callingActivity.equals("Reg")){
            Intent intent = getIntent();
            currentUser = intent.getStringExtra("Username");
            // re-init calling activity;
            callingActivity="";
        }
        
        // Let journal fragment know which user is logged in.
        // necessary for transactions.
        JournalFragment.username = currentUser;
        // Let NutriCart fragment know which user is logged in.
        // necessary for transactions.
        NutriCartFragment.username = currentUser;
        // side bar data
        journalSize = db.getSizeJournal(currentUser);
        cartSize = db.getSizeNutriCart(currentUser);
        
        Log.v("DEBUG", "User logged in is" + currentUser);

        
        
        /***********************************************************************************************************************
        /*
         * Nav drawer building and Action Toggle handling.
         */
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to List array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Add Food
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Library
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Journal (with counter)
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, Integer.toString(journalSize)));
		// RSS
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// NutriCart
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, Integer.toString(cartSize)));
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new NavMenuItemClickListener()); // set the listener for drawer list

		
		adapter = new NavDrawerListAdapter(getApplicationContext(),			// set the nav drawer list adapter
				navDrawerItems);
		mDrawerList.setAdapter(adapter);	// set data to list
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// create toggle button and Layout for toggle states
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		
		
		// set listener for draw menu
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	// Slide menu item click listener 
	private class NavMenuItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click: Sync user data to cloud.
		switch (item.getItemId()) {
		case R.id.sync:
			Log.v("DEBUG", "Starting Async Task..");
        	Toast.makeText(this, "Syncing User Statistics to Cloud . . .", Toast.LENGTH_SHORT).show();
        	
        	buildSyncData();
			SyncUserTask sync = new SyncUserTask();
			sync.setUserData(reportData);
			sync.execute();
			
        	Toast.makeText(this, "Data sent to cloud.", Toast.LENGTH_SHORT).show();

			
			return true;
			
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public void buildSyncData(){
		
		reportData = new UserReport();
		
		profile = db.getUserDetails(currentUser);
		String[] nutriTitles 	= getResources().getStringArray(R.array.nutrient_names); 
		// Get current days entries from Journal
        ArrayList<Food> foods 			= new ArrayList<Food>();
		foods 			= db.selectFoodsForReport(calculateDate(0), currentUser);
		monitor = new DietMonitor(profile, nutriTitles);
		nutrientTotals = monitor.calculateDailyReport(foods);
		
		reportData.setName(currentUser);
		reportData.setData(nutrientTotals);
		
	}
	
	
	

	// Called when invalidateOptionsMenu() is triggered
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.sync).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	
	 // Diplaying fragment view for selected nav drawer list item

	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		FragmentManager fm = getFragmentManager();
		DialogFragment diaFragment = null;
		
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			//fragment = new NewFoodFragment();
			diaFragment = new NewFoodFragment();
			diaFragment.show(fm, "this");
			break;
		case 2:
			fragment = new FoodBankFragment();
			break;
		case 3:
			fragment = new JournalFragment();
			JournalFragment.date = calculateDate(0);
			Log.v("DEBUG", "Date Today" + JournalFragment.date);
			
			break;
		case 4:
			fragment = new RSSFragment();
			break;
		case 5:
			fragment = new NutriCartFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	//When using the ActionBarDrawerToggle, you must call it during
	//onPostCreate() and onConfigurationChanged()...
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	
	// Handle barscan result.
	// Query database with ean string if scanResult found.
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		    
	    super.onActivityResult(requestCode, resultCode, intent);
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		boolean found;
		
		if (scanResult != null) { 
			// retrieve scan result from the barcode ZXING app.
			scanContent = scanResult.getContents();		
			//String scanFormat = scanResult.getFormatName();
			found = true;				
		}else{						
				found = false;
				Intent i = new Intent(this, TestActivity.class);
				i.putExtra("Code", scanContent);
				startActivity(i); 
		}	
		// Found implies the EAN has been recieved from the ZXING application.
		// If food ean is found in database search, search the SYSTEM_FOOD_TABLE for all food data relating to EAN.
		// 1. Query System_Table for ean
		// 2. If a match is found, get all food data from System_Table
		// 3. Open a new Food Facts Activity & set calling Activity to "Barscan" for appropriate Action Bar menu settings.
		// 3. If no match found, Create Alert Dialog ask user to enter manually.
		
		// If scan reuslt is found, query system table for food data.
		// If the system table holds a result, start next activity, with food data.
		if(found = true){
			db = new DatabaseManager(this);
			
			if(db.searchEAN(scanContent)){
				Food food = new Food();
				// search the SYSTEM_FOOD_TABLE. App data.
				food = db.selectFood(scanContent);
				
				
				FoodNutritionFactsActivity.callingActivity = "BarScan";
				Intent i = new Intent(this, FoodNutritionFactsActivity.class);
				i.putExtra("Food", food);
				startActivity(i); 
		}else{
			// if No match has been found in the system database
			// as the user would they like to entry the food manually.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nutrition Intuition");
            builder.setMessage("Food Not Found! Enter Manually?");  
             
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
            	// call the manual entry, auto fill the EAN for the user
   				Intent intent = new Intent(MainActivity.this, NewFoodManualEntry.class);
   				intent.putExtra("EAN", scanContent);
   				startActivity(intent);
                dialog.dismiss();
               }
            });
              
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
            });
             
             
             AlertDialog alert = builder.create();
             alert.show();
			
			
			
		}	
		}// end if
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
    
}
