package com.malleamus.speckle;

import java.util.Hashtable;
import java.util.Map;


public abstract class UI {
	
	private Map<String, View> viewRegistry = new Hashtable<String, View>();
	
	public abstract View getDefaultView();
	
	public void registerView(String tag, View view) {
		viewRegistry.put(tag, view);
	}
	
	public void withdrawView(String tag) {
		viewRegistry.remove(tag);
	}
	
	public boolean hasView(String tag) {
		return viewRegistry.containsKey(tag);
	}
	
	public View getView(String tag) {
		return viewRegistry.get(tag);
	}
}
