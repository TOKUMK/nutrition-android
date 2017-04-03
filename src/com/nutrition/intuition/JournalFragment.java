package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.nutrition.intuition.adapter.JournalAdapter;
import com.nutrition.intuition.adapter.NutriCartAdapter;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.model.JournalEntry;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class JournalFragment extends ListFragment implements OnItemClickListener{
	

		
	//private ArrayList<String> 			journalEntries = new ArrayList<String>();  // Data for array adapter
	private View rootView ;
	private DatabaseManager db;
	private ViewGroup container;
	private ArrayList<JournalEntry> journalEntries = new ArrayList<JournalEntry>();
	public static String date;
	
	public static String username;
	
	public JournalFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        rootView = inflater.inflate(R.layout.fragment_journal, container, false);
		this.container = container;        
        
        Log.v("DEBUG", "Date is " + date);

        db = new DatabaseManager(getActivity());
        
        journalEntries = db.selectFromJournal(date, username);
        
        TextView tvDate = (TextView) rootView.findViewById(R.id.date);
        
        if(date.equals( calculateDate(0))){
        	tvDate.setText("Date: Today" );
        }else{
        	tvDate.setText("Date: " + date ) ;
        }
        
        for(int i = 0; i < journalEntries.size();i++){
        	Log.v("DEBUG JOURNAL", "String values" + journalEntries.get(i).toString());
        }
        
        Log.v("DEBUG", "Dates are " + date + " " + calculateDate(0));
        
        
        // add to result set to data struct and adapter Replace with Journal Entries from cursor.		
		JournalAdapter adapter = new JournalAdapter(inflater.getContext(), R.layout.list_journal_entry,
												journalEntries);
        setFonts(rootView);
		setHasOptionsMenu(true);
		setListAdapter(adapter);  
		return rootView;
    }
	
	
	// Action Bar layout and functionality
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.journal_actions, menu);
       super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager;
        FragmentTransaction ft;
    	Intent intent;
    	String d;
    	db = new DatabaseManager(getActivity());
    	
        // Handle item selection
        switch (item.getItemId()) {
       
        case R.id.journal_today:
            manager = getActivity().getFragmentManager();
            ft = manager.beginTransaction(); 
            // Need to calculate chosen date.
            this.date=calculateDate(0);
            this.onDestroy();
            ft.remove(this);      
            ft.replace(container.getId(),this);//container is the ViewGroup of current fragment
            Log.v("DEBUG", "Date Yesterday is " + date);
            ft.addToBackStack(null);   
            ft.commit();

            return true;        
 
        case R.id.journal_yesterday:
            manager = getActivity().getFragmentManager();
            ft = manager.beginTransaction(); 
            // Need to calculate chosen date.
            this.date=calculateDate(1);
            this.onDestroy();
            ft.remove(this);      
            ft.replace(container.getId(),this);
             //container is the ViewGroup of current fragment
            Log.v("DEBUG", "Date Yesterday is " + date);
            ft.addToBackStack(null);   
            ft.commit();

            return true;
            
        case R.id.journal_3:
            manager = getActivity().getFragmentManager();
            ft = manager.beginTransaction();            
            this.date=calculateDate(2);
            this.onDestroy();
            ft.remove(this);      
            ft.replace(container.getId(),this);
             //container is the ViewGroup of current fragment
            ft.addToBackStack(null);   
            ft.commit();
            Log.v("DEBUG", "Date Today - 2 is " + date);
        	
            return true;
        	
        case R.id.journal_4:
            manager = getActivity().getFragmentManager();
            ft = manager.beginTransaction();            
            this.date=calculateDate(3);
            this.onDestroy();
            ft.remove(this);      
            ft.replace(container.getId(),this);
             //container is the ViewGroup of current fragment
            ft.addToBackStack(null);   
            ft.commit();
            
            Log.v("DEBUG", "Date Today - 3 is " + date);
        	return true;
        	
        case R.id.journal_5:
            manager = getActivity().getFragmentManager();
            ft = manager.beginTransaction();            
            this.date=calculateDate(4);
            this.onDestroy();
            ft.remove(this);      
            ft.replace(container.getId(),this);
             //container is the ViewGroup of current fragment
            ft.addToBackStack(null);   
            ft.commit();
            Log.v("DEBUG", "Date Today - 4 is " + date);
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Needed to establish the previous calling activity 
        // (in this case Journal). Variable will then be used 
        // to determine FoodNutrtionActivity action bar layout.
        FoodNutritionFactsActivity.callingActivity="Journal";
        
		TextView tvFoodName 	= (TextView) v.findViewById(R.id.tvFoodName);
		TextView tvServingSize 	= (TextView) v.findViewById(R.id.tvServingSize);
		TextView tvTime 		= (TextView) v.findViewById(R.id.tvTime);
		
		


		final String foodName 	= (String) tvFoodName.getText();
		String sSize			= (String) tvServingSize.getText();
		final String sTime 			= (String) tvTime.getText();

		final int intSize 		= Integer.parseInt(sSize);
		
		Food food = db.selectFood(journalEntries.get(position).getFoodName(),journalEntries.get(position).getServingSize());
		Log.v("DEBUG", "Food item Found and to be passed is " + food.toString());
		
		Intent intent = new Intent (getActivity(), FoodNutritionFactsActivity.class);
		intent.putExtra("Food", food );
		startActivity(intent);     
		
		// On long click item - dialog allowing user to delete from Journal table.
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, final long id) {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("Nutrition Intuition");
               builder.setMessage("Remove Journal Entry?");  
                
               builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                       // Code that is executed when clicking YES  
                	  db.deleteFromJournal(foodName, intSize, sTime);
                	  
                	  updateList();
                	                  	  
                       dialog.dismiss();
                  }
               });
                 
               builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                
                       // Code that is executed when clicking NO
                       dialog.dismiss();
                  }
               });
                
                
                AlertDialog alert = builder.create();
                alert.show();
                updateListView();
                return true;
            }
        });
    }

	// Updates list after item is deleted 
	public void updateListView() {
        rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_journal, container, false);    
        journalEntries = db.selectFromJournal(date, username);
		JournalAdapter adapter = new JournalAdapter(getActivity().getLayoutInflater().getContext(), R.layout.list_journal_entry,
				journalEntries);
	    setListAdapter(adapter);
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
	
	public void updateList() {
//      rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_nutri_cart, container, false);
//      NutriCartAdapter adapter = new NutriCartAdapter(getActivity().getLayoutInflater().getContext(), R.layout.list_nutri_cart_item, foods);
//	    setListAdapter(adapter);

		   // setListAdapter(adapter);
		
			db = new DatabaseManager(getActivity());
			// get journal data
			// get list
			// set atapter
			//register for context menue>
			
	        journalEntries = db.selectFromJournal(date, username);
			JournalAdapter adapter = new JournalAdapter(getActivity().getLayoutInflater().getContext(), R.layout.list_journal_entry,
					journalEntries);
			 rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_journal, container, false);    

	    	setListAdapter(adapter);
	    	
	/*		
			  list=(ListView)findViewById(R.id.list);   
			  adapter=new ListAdapter(this, data , data2);*/
			  //list.setAdapter(adapter);
			      //registerForContextMenu(list);
			
	}
	
	public void setFonts(View rootView){
		// Set font
		TextView tvHeader = (TextView) rootView.findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    tvHeader.setTypeface(tf); 
	    
	    // Set font Date
		TextView tvDate = (TextView) rootView.findViewById(R.id.date);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");
	    tvDate.setTypeface(tf); 
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
