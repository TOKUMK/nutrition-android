package com.nutrition.intuition.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.nutrition.intuition.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class NewFoodNutrientAdapter extends BaseAdapter{
	
	private Context context;
	private Layout layout;
	int layoutResourceId;
	
	private ArrayList<String> nutriTitles 	= new ArrayList<String>();
	private HashMap<String, Integer> map;
	
    TextView tvNutrientName;
    EditText tvNutrientValue;
    int updatedVal;
    

	
	
	public NewFoodNutrientAdapter(Context context, int layoutResourceId, String[] titles){
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		map = new HashMap<String, Integer>();
		
		int j = 0;
		for(int i = 0; i < titles.length; i++){
			j =+i;
			nutriTitles.add(titles[i]);
			map.put(titles[i], j);

		}

			//Log.v("DEBUG", "Map IS" + map.get("Calories"));

		
	} 
	

	
	@Override
	public int getCount() {
		return nutriTitles.size();
	}

	@Override
	public Integer getItem(int position) {
		// TODO Auto-generated method stub
		//return tv.getText(position);
		
		return Integer.parseInt( tvNutrientValue.getText().toString());
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		// if convert view is null, it is a new view and not a recycled one.
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_new_food, null);

            
            // create holder and set tag to View.
            holder = new ViewHolder();
            holder.nutrientName = (TextView) convertView.findViewById(R.id.tvNutrientName);
            holder.nutrientValue = (EditText) convertView.findViewById(R.id.tvNutrientValue);
            convertView.setTag(holder);
                       
        }else{
        	// View recycled.
        	// no need to inflate
        	// no need to findViews by id
        	holder = (ViewHolder) convertView.getTag();
        }
			
        tvNutrientName 		= (TextView) convertView.findViewById(R.id.tvNutrientName);
        tvNutrientValue  	= (EditText) convertView.findViewById(R.id.tvNutrientValue);
        
/*   	  holder.nutrientValue.addTextChangedListener(new TextWatcher() {
     	 
   	   public void afterTextChanged(Editable s) {
   	   }
   	 
   	   public void beforeTextChanged(CharSequence s, int start, 
   	     int count, int after) {
   	   }
   	 
   	   public void onTextChanged(CharSequence s, int start, 
   	     int before, int count) {
          	// run when focus is lost
          	String key = tvNutrientName.getText().toString();
              final String value = tvNutrientValue.getText().toString();         // get the value from the EditText
              // Your code to update hashmap
              Log.v("DEBUG", "________________ ");
          	Log.v("DEBUG", "Nutrient Name  " + nutriTitles.get(position));
          	Log.v("DEBUG", "We have lost focus	" + position);
          	Log.v("DEBUG", "We have lost focus	" + position);
          	Log.v("DEBUG", "Nutri value	" + value);

              Log.v("DEBUG", "________________ ");
   	   }
   	  });*/
        holder.nutrientValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { 
                	if(tvNutrientValue.getText().toString() != ""){
                    	// run when focus is lost
                    	String key = tvNutrientName.getText().toString();
                        final String value = tvNutrientValue.getText().toString();         // get the value from the EditText
                        // Your code to update hashmap
                        Log.v("DEBUG", "________________ ");
                    	Log.v("DEBUG", "Nutrient Name  " + nutriTitles.get(position));
                    	Log.v("DEBUG", "We have lost focus	" + position);
                    	Log.v("DEBUG", "We have lost focus	" + position);
                    	Log.v("DEBUG", "Nutri value	" + value);

                        Log.v("DEBUG", "________________ ");
                	}
                	             	


                }
            }
        });
        
 
        /*holder.nutrientValue.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable editable) {
               
            	Log.v("DEBUG", "Position is [][][]" + position);
            	
            	for(String title: map.keySet()){
            		if(title.equals(nutriTitles.get(position))){
            			map.put(nutriTitles.get(position).toString(),Integer.parseInt(editable.toString()));
            			Log.v("DEBUG", "Values put in map:  " + nutriTitles.get(position).toString() + " & " +Integer.parseInt(editable.toString()));
            		}
            	}

            }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        });*/
        
        
////        tvNutrientValue.addTextChangedListener(watcher);
//       // map.get() where key. equals nutriList.position
//        
//        for(int i = 0;i < nutriTitles.size();i++){
//        	if(map.get(i).equals(nutriTitles.get(position))){
//        		Log.v("DEBUG", "Hello");
//        	}
//        }
        	//get nutrient name at list position
        	// get value mapped to this position
        	
        	//int value  = map.get(nutrient);
        	

        
    		String nutrient = 		nutriTitles.get(position);
        	tvNutrientName.setText(nutrient);
        	Log.v("DEBUG", "Hello position is	" + position);
        	//tvNutrientValue.setText(String.valueOf(value));
        			

        setFonts();
        return convertView;
		
	}
	private class MyTextWatcher implements TextWatcher{
		 
		  private View view;
		  
		  private MyTextWatcher() {
			  }
		  private MyTextWatcher(View view) {
		   this.view = view;
		  }
		 
		  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		   //do nothing
		  }
		  public void onTextChanged(CharSequence s, int start, int before, int count) { 
		   //do nothing
		  }
		  public void afterTextChanged(Editable s) {
		    
		  
		     

		   return;
		  }
	}
	
	public void setFonts(){
       	// FONT Settings
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvNutrientName.setTypeface(tf);
	}
	
	
}