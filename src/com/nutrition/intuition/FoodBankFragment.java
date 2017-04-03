package com.nutrition.intuition;

import java.util.ArrayList;
import java.util.Arrays;

import com.nutrition.intuition.adapter.FoodCatAdapter;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FoodBankFragment extends ListFragment implements OnItemClickListener {
	
	Button fruitVegCat , meatFishCat, grainsCat, otherCat, legumesCat;
	private ArrayList<String> categories;
	private ListView lv;
	private FoodCatAdapter adapter;
	private View rootView;
	
	public FoodBankFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_food_bank, container, false);
        setFonts();
        
        String[] resList = getResources().getStringArray(R.array.food_cat_items);
        categories = new ArrayList( Arrays.asList(resList));
         
                adapter = new FoodCatAdapter(inflater.getContext(), R.layout.list_food_cat, categories);

	    
        setListAdapter(adapter);  
        return rootView;
    }
	
	public void onActivityCreated(){
	       lv = getListView();
	        //setListAdapter(adapter);  

	}
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
		Intent intent = new Intent (getActivity(), FoodListActivity.class);
		intent.putExtra("Cat", categories.get(position).toString());
		startActivity(intent);

        
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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
