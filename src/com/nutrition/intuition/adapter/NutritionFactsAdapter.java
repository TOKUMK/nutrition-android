package com.nutrition.intuition.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.nutrition.intuition.HomeFragment;
import com.nutrition.intuition.R;
import com.nutrition.intuition.model.NutritionFactItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class NutritionFactsAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<NutritionFactItem> nutriItems;
	private Layout layout;
	int layoutResourceId;
	
	public NutritionFactsAdapter(Context context, int layoutResourceId, ArrayList<NutritionFactItem> items){
		this.context = context;
		this.nutriItems = items;
		this.layoutResourceId = layoutResourceId;
	}
	
	public NutritionFactsAdapter(Context context, Layout layout, ArrayList<NutritionFactItem> items){
		this.context = context;
		this.nutriItems = items;
		this.layout = layout;
	}
	
	@Override
	public int getCount() {
		return nutriItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		String nutriName = "";
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_nutrition_facts, null);
        }
         
		
        TextView tvNutrientName 		= (TextView) convertView.findViewById(R.id.tvNutrientName);
        TextView tvNutrientValue  		= (TextView) convertView.findViewById(R.id.tvNutrientValue);
        TextView tvdailyPercentage 		= (TextView) convertView.findViewById(R.id.tvDailyPercentage);
        
        nutriName = nutriItems.get(position).getNutrientTitle();      	
        tvNutrientName.setText(nutriName);
        tvNutrientValue.setText(Integer.toString(nutriItems.get(position).getNutrientValue()));
        
        
        // calculate percentage rda % amounts of the nutrient
        int  nutrientAmt, gdaTargetValue= 0;
        float  answer = 0;
        
        nutrientAmt = nutriItems.get(position).getNutrientValue(); // nutrient amount value found in food
        HashMap<String,Integer> gdas = HomeFragment.monitor.getNutriTargetsGDA(); // nutrient amount value of user gda
        gdaTargetValue = gdas.get(nutriName);
        
        answer = (float) nutrientAmt /  (float)gdaTargetValue * 100;
        
        int test = Math.round(answer);
        
        if(test > 1000){
    		tvdailyPercentage.setText("100 %");
        }else{
    		tvdailyPercentage.setText(Integer.toString( Math.round(answer)) + "%");

        }
 
   
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
		
	    tvNutrientName.setTypeface(tf);
	    tvNutrientValue.setTypeface(tf);
	    
        return convertView;
	}
	
	
}