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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RecipesFragment extends ListFragment implements OnItemClickListener {
	
	Button fruitVegCat , meatFishCat, grainsCat, otherCat, legumesCat;
	private ArrayList<String> categories;
	private ListView lv;
	private FoodCatAdapter adapter ;
	
	public RecipesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		View rootView;
		String[] resList = null;
        
        if(resList == null){
        	// set No recipes View
        	 rootView = inflater.inflate(R.layout.fragment_no_recipes, container, false);
             setFonts(rootView);

        }else{
        	
            rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
            setFonts(rootView);

        }
        
        
        
       /* String[] resList = getResources().getStringArray(R.array.food_cat_items);
        categories = new ArrayList( Arrays.asList(resList));*/
         
        
//        adapter = new FoodCatAdapter(inflater.getContext(), R.layout.fragment_recipes, categories);

	    setHasOptionsMenu(true);
//        setListAdapter(adapter);  
        return rootView;
    }
	
	public void onActivityCreated(){
	       lv = getListView();
	        //setListAdapter(adapter);  

	}
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	/*Log.v("DEBUG", "DEBUG 3 [] Item is" + categories.get(position).toString());
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        
        
		Intent intent = new Intent (getActivity(), FoodListActivity.class);
		intent.putExtra("Cat", categories.get(position).toString());
		startActivity(intent);
*/
        
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.recipe_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.new_recipe:
        	
        	Intent intent = new Intent(getActivity(), NewRecipe.class);
        	startActivity(intent);
        	Log.v("DEBUG", "Hello");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    // ToDo return boolean.
 	public void setFonts(View rootView){
 		// Set font
 		TextView tvHeader = (TextView) rootView.findViewById(R.id.header);
 		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
 	            "fonts/QuaverSans.otf");	
 				//"fonts/LeagueGothicRegular.otf");
 	    tvHeader.setTypeface(tf); 
 	}

	
}
