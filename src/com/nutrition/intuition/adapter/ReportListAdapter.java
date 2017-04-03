package com.nutrition.intuition.adapter;

import java.util.ArrayList;

import com.nutrition.intuition.R;
import com.nutrition.intuition.model.ReportItem;

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



public class ReportListAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<ReportItem> reportItems;
	private Layout layout;
	int layoutResourceId;
	
	public ReportListAdapter(Context context, int layoutResourceId, ArrayList<ReportItem> items){
		this.context = context;
		this.reportItems = items;
		this.layoutResourceId = layoutResourceId;
	}
	
	public ReportListAdapter(Context context, Layout layout, ArrayList<ReportItem> items){
		this.context = context;
		this.reportItems = items;
		this.layout = layout;
	}
	
	@Override
	public int getCount() {
		return reportItems.size();
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
		
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_report, null);
        }
         
		
        TextView tvNutrientName 	= (TextView) convertView.findViewById(R.id.tvNutrientName);
        TextView tvNutrientValue  	= (TextView) convertView.findViewById(R.id.tvNutrientValue);
        ProgressBar pBar			= (ProgressBar) convertView.findViewById(R.id.progressBar);
        TextView tvPbarValue 		= (TextView) convertView.findViewById(R.id.tvPbarValue);
        
      	
        tvNutrientName.setText(reportItems.get(position).getNutrientTitle());
        tvNutrientValue.setText(Integer.toString(reportItems.get(position).getCurrentIntakeValue()));
        tvPbarValue.setText(tvNutrientValue.getText() + " / " + Integer.toString(reportItems.get(position).getNutriTargetValue()));
//       	pBar.setProgress(reportItems.get(position).getCurrentIntakeValue());
       	

       	
       int  currrentIntake, gdaTargetValue= 0;
       float  answer = 0;
       currrentIntake = reportItems.get(position).getCurrentIntakeValue();
       gdaTargetValue = reportItems.get(position).getNutriTargetValue(); 
       
       Log.v("DEBUG", "Answer : " + currrentIntake);
       Log.v("DEBUG", "Answer : " + gdaTargetValue);

       
       answer = (float)currrentIntake /  (float)gdaTargetValue * 100;
      
       int progress;
       progress = Math.round(answer);
       
       Log.v("DEBUG", "Answer : " + tvNutrientName.getText() + " " + progress);
       
   
       
       pBar.setProgress(progress);
       pBar.setMax(100);

       	//tvPbarValue.setText(tvNutrientValue.getText() + " / 2134" ); 
       	
       	// FONT Settings
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvNutrientName.setTypeface(tf);
	    tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvNutrientValue.setTypeface(tf);
	    //tvNutrientValue.textSize("22sp");

        
        /*
        // displaying count
        // check whether it set visible or not
        if(reportItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        } */
        
        return convertView;
		
	}
	
	
}