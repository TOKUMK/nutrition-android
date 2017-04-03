package com.nutrition.intuition.adapter;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.nutrition.intuition.R;
import com.nutrition.intuition.model.JournalEntry;

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



public class JournalAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<JournalEntry> journalEntries;
	private Layout layout;
	int layoutResourceId;
	
	public JournalAdapter(Context context, int layoutResourceId, ArrayList<JournalEntry> entries){
		this.context = context;
		this.journalEntries = entries;
		this.layoutResourceId = layoutResourceId;
	}
	
	public JournalAdapter(Context context, Layout layout, ArrayList<JournalEntry> entries){
		this.context = context;
		this.journalEntries = entries;
		this.layout = layout;
	}
	
	@Override
	public int getCount() {
		return journalEntries.size();
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
            convertView = mInflater.inflate(R.layout.list_journal_entry, null);
        }
         
		
		ImageView icon				= (ImageView) convertView.findViewById(R.id.image);
        TextView  tvFoodName 		= (TextView) convertView.findViewById(R.id.tvFoodName);
        TextView  tvServingSize 	= (TextView) convertView.findViewById(R.id.tvServingSize);
        TextView  tvTime 			= (TextView) convertView.findViewById(R.id.tvTime);
       
        // TODO add Data and Serving Size. Consider for revision (calories, list item ordering..)
        tvFoodName.setText(journalEntries.get(position).getFoodName());
        tvServingSize.setText(String.valueOf(journalEntries.get(position).getServingSize()));
        tvTime.setText(String.valueOf(journalEntries.get(position).getTime()));
       
        // FONT Settings
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
	            "fonts/QuaverSans.otf");
	    tvFoodName.setTypeface(tf);

        return convertView;		
	}
	
	
}