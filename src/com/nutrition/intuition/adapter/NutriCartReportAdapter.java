package com.nutrition.intuition.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import com.nutrition.intuition.R;
import com.nutrition.intuition.model.ReportItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NutriCartReportAdapter extends BaseAdapter {
	
	private Context context;
	private HashMap<String, Integer> nutriTotals;
	private ArrayList<String> nutriNames;
	private int layout;
	
	
	public NutriCartReportAdapter(Context context, int layoutId, HashMap<String, Integer> nutriTotals, ArrayList<String> nutriNames){
		this.context 		= context;
		this.layout 		= layoutId;
		this.nutriTotals 	= nutriTotals;
		this.nutriNames		= nutriNames;
	}

	@Override
	public int getCount() {
		return nutriNames.size();
	}

	@Override
	public Object getItem(int position) {		
		return nutriNames.get(position);
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
            convertView = mInflater.inflate(R.layout.list_item_nutri_cart_report, null);
        }
         
        
        TextView tvNutrientName  = (TextView) convertView.findViewById(R.id.tvNutrientName);
        tvNutrientName.setText(nutriNames.get(position)); 
        
        TextView tvNutrientValue = (TextView) convertView.findViewById(R.id.tvNutrientValue);
        tvNutrientValue.setText(String.valueOf(nutriTotals.get(nutriNames.get(position))));
        
        ProgressBar pBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        
        int total = nutriTotals.get(nutriNames.get(position));
        
        pBar.setProgress(total);
        pBar.setMax(50);
        pBar.setRotation(180);

        
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvNutrientName.setTypeface(tf);		
        
        return convertView;
	}

}
