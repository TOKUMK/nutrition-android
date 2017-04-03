package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nutrition.intuition.adapter.NutriCartAdapter;
import com.nutrition.intuition.barscan.IntentIntegrator;
import com.nutrition.intuition.model.Food;
import com.nutrition.intuition.persistence.DatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class NutriCartFragment extends ListFragment {
	
	private ArrayList<Food> foods; 	// data model for user interface
	private ArrayList<Food> basket;	// data model for tracking checked and unchecked products.
	//private HashMap<String, Integer> basket;
	private ArrayList<String> sbasket;

	private View rootView;
	private DatabaseManager 	db;

	private ListView list;
	private ViewGroup container;
	public static String username;
	
	public NutriCartFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		this.container = container;
        rootView = inflater.inflate(R.layout.fragment_nutri_cart, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        setFonts();
        
        db 		= new DatabaseManager(getActivity()); 	// for data outout <NAME, SERVING SIZE>
        basket 	= new ArrayList<Food>(); 				// tracks names crossed / uncrossed on list.	
        // returns a list of food objects in nutriCart table.
        // TODO *optimize* return a list Food objects with all data.
        // Removes need to query the DB a second time.
        
        
    	foods 	= db.loadNutriCart(MainActivity.currentUser); 

    	// activity starts with all foods loaded from the nutriCart table in the basket.
    	// init basket.
    	for(int i = 0; i < foods.size(); i++ ){
    		basket.add(foods.get(i));
    	}
    	
    	// show all foods in basket currently..
    	for(int i = 0; i < basket.size(); i++){
    		Log.v("DEBUG", "Foods in basket are " + basket.get(i).toString());
    	}
  
        setHasOptionsMenu(true);

        NutriCartAdapter adapter = new NutriCartAdapter(inflater.getContext(), R.layout.list_nutri_cart_item, foods);
        setListAdapter(adapter);
        
        return rootView;
        
        
    }
	
	/*
	 *  for each row that has a strikeout, get its row number and delete from the list.
	 *  q! what data structure do we use to hold the shopping cart food names.
	 *  how do we manage this data structure: add and delete..??
	 * 
	 * 
	 * 
	 */
	
	
	public void onListItemClick(ListView l, View v, int position, long id){
		
		TextView tvFoodName 	= (TextView) v.findViewById(R.id.tvFoodName);
		TextView tvServingSize 	= (TextView) v.findViewById(R.id.tvServingSize);

		final String foodName = (String) tvFoodName.getText();
		String sSize	= (String) tvServingSize.getText();
		final int intSize 	= Integer.parseInt(sSize);
		
		
		// On long click item - dialog allowing user to delete from nutricart table.
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, final long id) {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("Nutrition Intuition");
               builder.setMessage("Remove item from cart?");  
                
               builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                       // Code that is executed when clicking YES  
                	  db.deleteFromCart(foodName, intSize);
                	  
                	  // update
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
                //updateListView();
                return true;
            }
        });

		

		
		Log.v("DEBUG", "Name of food clicked: " + foodName);
		boolean found = false;

		
		// NULL POINTERS POTENTIAL foods.size > basket.size()..
		for(int i = 0; i < basket.size(); i++){
			// the food food item selected, is already found to be in the basket, do nothing / or remove?
			
			// if food slected found in basket, remove from basket
			if(basket.get(i).getName().equals(foodName.toLowerCase()) && basket.get(i).getServingSize() ==intSize){
				basket.remove(i);
			}else{
				// add to basket.
				Food f = new Food();
				f.setName(foodName.toLowerCase());
				f.setServingSize(intSize);
				basket.add(f);
			}
		}
			

		// HANDLE TEXT STRIKES..
		// if flag is set, unstrike text
		if ((tvFoodName.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0){
			tvFoodName.setPaintFlags(0);	// unstrike / unset flag 
			 Log.v("DEBUG", "UNSTRIKING " + foodName);

		}else{
			// Else strike text and set flag
			tvFoodName.setPaintFlags(tvFoodName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			 Log.v("DEBUG", "STRIKING " + foodName);
		}
		
	}
	
	
	// Updates list after item is deleted from cart.
	public void updateList() {
//        rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_nutri_cart, container, false);
//        NutriCartAdapter adapter = new NutriCartAdapter(getActivity().getLayoutInflater().getContext(), R.layout.list_nutri_cart_item, foods);
//	    setListAdapter(adapter);
		
			db = new DatabaseManager(getActivity());
			// get journal data
			// get list
			// set adapter
			//register for context menue>
			
	    	foods 	= db.loadNutriCart(MainActivity.currentUser); 
	    	NutriCartAdapter adapter = new NutriCartAdapter(getActivity().getLayoutInflater().getContext(), R.layout.list_nutri_cart_item, foods);
	    	rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_nutri_cart, container, false);
	    	
	    	
	    	setListAdapter(adapter);
	    	
	/*		
			  list=(ListView)findViewById(R.id.list);   
			  adapter=new ListAdapter(this, data , data2);*/
			  //list.setAdapter(adapter);
			      //registerForContextMenu(list);
			
	}
	
	
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.nutri_report:
        	
        	intent = new Intent(getActivity(), NutriCartReport.class);
        	
        	//intent.putExtra("data", foods);
        	
        	// return a list of
        	//intent.putStringArrayListExtra("data", (ArrayList<String>) basket);
        	intent.putExtra("data",  basket);
        	
        	updateList();
        	
        	Log.v("DEBUG NUTRI", "BASKET CASE IS " + basket.size());
        	
        	startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	public void setFonts(){
		// Set font
		TextView tvHeader = (TextView) rootView.findViewById(R.id.header);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    tvHeader.setTypeface(tf); 
	    
		
	}
	
	
}
