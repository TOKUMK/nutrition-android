package com.nutrition.intuition.rss;


import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.nutrition.intuition.R;
import com.nutrition.intuition.adapter.RSSAdapter;
import com.nutrition.intuition.model.RSSRowItem;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class XMLAsyncTask extends AsyncTask<String, Void, String>{

    public ArrayList<RSSRowItem> rssItems = null;
    public Context context;
    public ListView listView;

    
    public XMLAsyncTask(Context context, ArrayList<RSSRowItem> items){
    	this.rssItems = items;
    	this.context = context;
    }
    
    public XMLAsyncTask(Context context, ArrayList<RSSRowItem> items, ListView listView){
    	this.rssItems = items;
    	this.context = context;
    	this.listView = listView;
    }
    
    public XMLAsyncTask(ArrayList<RSSRowItem> items){
    	this.rssItems = items;
    }

    private String getNodeValue(Element entry, String tag){
    	Element element = (Element) entry.getElementsByTagName(tag).item(0);
    	return 	element.getFirstChild().getNodeValue();
    }
    
    private String downloadParseXML(String url){
    	try{
    		// Open input stream from RSS URL/
    		InputStream in = new java.net.URL(url).openStream();
    		// create XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // parse and get nutrition XML elements
            Document docModel = builder.parse(in);
            Element documentElement = docModel.getDocumentElement();
             
            // get all XML children called item
            NodeList nodes = documentElement.getElementsByTagName("item");
            // for each node create an RSS Row Item
            for (int i = 0; i < nodes.getLength(); i++) {
                Element entry = (Element) nodes.item(i);
                // get the nodes from item that are needed
                String title = getNodeValue(entry, "title");
                String description = getNodeValue(entry, "description");
                String link = getNodeValue(entry, "link");
                // add them to your list
                rssItems.add(new RSSRowItem(title, description, link));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@Override
	protected String doInBackground(String... urls) {
		if(urls.length <=0)
			return null;
		else return downloadParseXML(urls[0]);
	}
	
	protected void onPostExecute(String result) {
		Log.v("DEBUG", "items" + rssItems.size());
        updateList(rssItems);
    }
	
	public void updateList(ArrayList<RSSRowItem> items) {
        
		RSSAdapter adapter = new RSSAdapter(context,
                R.layout.rss_row_item, items);    
        listView.setAdapter(adapter);
    } 
}



