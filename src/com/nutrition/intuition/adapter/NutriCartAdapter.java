package com.nutrition.intuition.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import com.nutrition.intuition.R;
import com.nutrition.intuition.model.Food;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NutriCartAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Food> foodItems;

	
	public NutriCartAdapter(Context context, int Layout, ArrayList<Food> foods){
		this.context = context;
		this.foodItems = foods;
	}

	@Override
	public int getCount() {
		return foodItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return foodItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_nutri_cart_item, null);
        }
         
        
        TextView tvFoodName 	= (TextView) convertView.findViewById(R.id.tvFoodName);
        TextView tvServingSize	= (TextView) convertView.findViewById(R.id.tvServingSize);

        tvFoodName.setText(foodItems.get(position).getName());
        tvServingSize.setText(String.valueOf(foodItems.get(position).getServingSize()));
        
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvFoodName.setTypeface(tf);
        
        return convertView;
	}

}
