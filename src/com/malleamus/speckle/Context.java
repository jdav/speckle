package com.malleamus.speckle;

import java.util.Properties;

public class Context extends Properties {

	private static final long serialVersionUID = 251381406373106048L;

	public Configuration getConfiguration() {
		return (Configuration) this.get("CONFIGURATION");
	}
	
	public void setConfiguration(Configuration config) {
		this.put("CONFIGURATION", config);
	}

	public UI getUI() {
		return (UI) this.get("UI");
	}

	public void setUI(Object objectFromConfig) {
		this.put("UI", objectFromConfig);
	}

}
