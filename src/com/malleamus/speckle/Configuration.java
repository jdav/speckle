package com.malleamus.speckle;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Configuration extends Properties {

	private static final long serialVersionUID = 158726625126670171L;

	public Configuration() {
		super();
	}
	
	public Configuration(String configFileString) {
		try {
			File file = new File(configFileString);
			load(new FileInputStream(file));
		} catch (Exception e) {
			System.out.println("ERROR: " + configFileString
					+ " is not a properties file.");
			System.exit(-1);
		}
	}
	
	public Class<?> getClass(String propertyName) {
		String className = getProperty(propertyName);

		if (className == null || className.isEmpty()) {
			System.out.println("ERROR: Config file must " + 
		                       "contain a property called " + 
					           propertyName + ".");
			System.exit(-1);
		}

		Class<?> cl = null;
		try {
			cl = Class.forName(className);
		} catch (Exception e) {
			System.out.println("ERROR: " + propertyName + 
					           " property must be a class. " + 
		                       className + " is not a class.");
			System.exit(-1);
		}

		return cl;
	}
	
	public Object getObject(String propertyName) {
		Class<?> cl = getClass(propertyName);
		Object obj = null;
		try {
			obj = cl.newInstance();
		} catch (Exception e) {
			System.out.println("ERROR: " + propertyName + " property must " +
					           "be a class with a parameterless constructor");
			System.exit(-1);
		}
		return obj;
	}
}
