package com.nutrition.intuition.model;


public class RSSRowItem {
	
	public String title = null;
    public String description = null;
    public String url = null;
		
    public RSSRowItem(String title, String description, String url) {
    	this.title = title;
    	this.description = description;
    	this.url = url;
	}
}