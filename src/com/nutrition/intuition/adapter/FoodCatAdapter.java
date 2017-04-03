package com.nutrition.intuition.adapter;

import java.util.ArrayList;

import com.nutrition.intuition.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodCatAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<String> categories = new ArrayList<String>();
	private int layout;
	private Layout Layout;
	
	public FoodCatAdapter(Context context, int layout, ArrayList<String> categories){
		this.context = context;
		this.layout = layout;
		this.categories = categories;		
	}
	

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {		
		return categories.get(position);
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
            convertView = mInflater.inflate(R.layout.list_food_cat, null);
        }
		
		// Set Icon and Text depending on the position.
        ImageView icon 		= (ImageView) convertView.findViewById(R.id.image);
        TextView tvCatName 	= (TextView)  convertView.findViewById(R.id.tvCatName);
        
        if(categories.get(position).toString().equals("Fruit and Vegatables")){
            icon.setImageResource(R.drawable.fruit_veg);
        }else if(categories.get(position).toString().equals("Cereals and Grains")){
            icon.setImageResource(R.drawable.grains);
        }else if(categories.get(position).toString().equals("Meat and Fish")){
            icon.setImageResource(R.drawable.meat_seafood);
        }else if(categories.get(position).toString().equals("Legumes")){
            icon.setImageResource(R.drawable.legumes);
        }else if(categories.get(position).toString().equals("Dairy")){
            icon.setImageResource(R.drawable.dairy);
        }else if(categories.get(position).toString().equals("Other")){
            icon.setImageResource(R.drawable.other);
        }
        
        tvCatName.setText(categories.get(position).toString()); 
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvCatName.setTypeface(tf);
        return convertView;
	}
	
	
	
	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_food_item, null);
        }
         
		// Set Icon and Text depending on the position.
        ImageView icon 		= (ImageView) convertView.findViewById(R.id.imageView);
        TextView tvCatName 	= (TextView) convertView.findViewById(R.id.tvFoodName);
        
        icon.setImageResource(R.drawable.dairy);
        tvCatName.setText(categories.get(position)); 
                
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvCatName.setTypeface(tf);

        return convertView;
	} */

}
