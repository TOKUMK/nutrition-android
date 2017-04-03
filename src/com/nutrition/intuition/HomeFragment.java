package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.nutrition.intuition.adapter.ReportListAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.ReportItem;
import com.nutrition.intuition.model.UserProfile;
import com.nutrition.intuition.monitor.DietMonitor;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends ListFragment {
	
	private TextView diet, weight, date;
	
	public String[] 					nutriTitles; // Nutrient Name resource
	
	private HashMap<String, Integer> 	nutriTargets = new HashMap<String, Integer>(); 	// <Map Nutrient Value of Users Target Values>
	private HashMap<String, Integer> 	nutrientTotals;									// Holds summation of nutri values.
	private ArrayList<Food> foods;

	private ArrayList<ReportItem> 		reportItems = new ArrayList<ReportItem>();  	// Holds Data for array adapter

	public  static DietMonitor monitor;
	private DatabaseManager db;
	private UserProfile profile;
	
	private View rootView ;
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setFonts(rootView);

        diet 	= (TextView) rootView.findViewById(R.id.diet);
        weight 	= (TextView) rootView.findViewById(R.id.weight);
        date 	= (TextView) rootView.findViewById(R.id.date);
       
        db 		= new DatabaseManager(getActivity());
        // Current user always set on application login.
		String username 	= MainActivity.currentUser;
        Log.v("DEBUG USERNAME", "Current User is " + username); 
        // Build user data profile
		UserProfile user 	= db.getUserDetails(username);

		// Output user data matrics to TextViews.
        diet.setText("Curent Objective: " + user.getDiet());
        weight.setText("Weight: " + Integer.toString(user.getWeight()) + " KG");
        date.setText("Date: Today");
                
        // load string array of nutrient titles used by the system. Needed for data mapping.
        nutriTitles 	= getResources().getStringArray(R.array.nutrient_names); 
        nutrientTotals 	= new HashMap<String, Integer>();
		
		// Get current days entries from Journal
        foods 			= new ArrayList<Food>();
		foods 			= db.selectFoodsForReport(calculateDate(0), username);
		
        Log.v("DEBUG JOURNAL", "Journal size of " + user.getName() + " is " + foods.size());
        
		monitor = new DietMonitor(user, nutriTitles);
		nutrientTotals = monitor.calculateDailyReport(foods);
		
		ReportListAdapter adapter = new ReportListAdapter(inflater.getContext(), R.layout.list_item_report,buildReportData(nutrientTotals,
																													monitor.getNutriTargetsGDA()));
        setHasOptionsMenu(false);		
        setListAdapter(adapter);  
		return rootView;
    }
	
	public ArrayList<ReportItem> buildReportData(HashMap<String, Integer> nutriTotals, HashMap<String, Integer> nutriTargets){
		
		// for each Journal Entry, build a report data object.
		// Report data structure: <food title, current, target>
		
		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>();
		ReportItem r;
		
		for(int i = 0; i < nutriTitles.length; i++){
			r = new ReportItem();
			r.setNutrientTitle(nutriTitles[i]);
			r.setCurrentIntakeVal(nutriTotals.get(nutriTitles[i]));
			r.setNutriTargetValue(nutriTargets.get(nutriTitles[i]));
			reportItems.add(r);
		}
		return reportItems;
	}
	
	public void setFonts(View rootView){
		// Set font
		TextView tvHeader = (TextView) rootView.findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    tvHeader.setTypeface(tf); 
		// Set font Diet
		TextView tvDiet = (TextView) rootView.findViewById(R.id.diet);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");
	    tvDiet.setTypeface(tf); 
	    // Set font Weight
		TextView tvWeight = (TextView) rootView.findViewById(R.id.weight);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");
	    tvWeight.setTypeface(tf); 
	    // Set font Date
		TextView tvDate = (TextView) rootView.findViewById(R.id.date);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");
	    tvDate.setTypeface(tf); 
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
		// Construct string in final formatting of DD-MM-YYYY
		String sDate = day + "-" + month + "-" + year;
		return sDate;
	}
	
	
}

