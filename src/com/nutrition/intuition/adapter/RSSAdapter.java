package com.nutrition.intuition.adapter;



import java.util.List;

import com.nutrition.intuition.R;
import com.nutrition.intuition.model.RSSRowItem;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class RSSAdapter extends ArrayAdapter<RSSRowItem> {
        
		Context context;
        List<RSSRowItem> items;
         
        public RSSAdapter(Context context, int resource, List<RSSRowItem> items) {
            super(context, resource, items);
            this.context = context;
            this.items = items;
        }
        
        private class ViewHolder {
            public TextView title;
            public TextView description;
        }
         
        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
        	if(rowView == null) {
        		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.rss_row_item, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                viewHolder.description = (TextView) rowView.findViewById(R.id.description);
                rowView.setTag(viewHolder);
            }
            // set the view
            ViewHolder holder = (ViewHolder) rowView.getTag();
            RSSRowItem item = items.get(position);
            holder.title.setText(item.title);
            holder.description.setText(item.description);

            return rowView;
        }
        

    }