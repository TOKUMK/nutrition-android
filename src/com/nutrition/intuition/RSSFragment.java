package com.nutrition.intuition;

import java.util.ArrayList;

import com.nutrition.intuition.model.RSSRowItem;
import com.nutrition.intuition.rss.XMLAsyncTask;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RSSFragment extends Fragment implements OnItemClickListener {
	
	private ListView listView;
	private ArrayList<RSSRowItem> items;
	
	public RSSFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
        
        TextView header= (TextView) rootView.findViewById(R.id.head);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	            "fonts/QuaverSans.otf");	
				//"fonts/LeagueGothicRegular.otf");
	    header.setTypeface(tf); 
        
      	// get the listView and set this to be the onItemClickListener
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
                
        items = new ArrayList<RSSRowItem>();
        
        new XMLAsyncTask(getActivity(), items, listView).execute("http://www.medicalnewstoday.com/rss/nutrition-diet.xml");
        
        return rootView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		RSSRowItem item = items.get(position);
	    // start browser with the url
		Intent articleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
		startActivity(articleIntent);
	    }

}
