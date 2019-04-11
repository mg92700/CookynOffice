package com.general.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Configuration {
	
	
	

InputStream inputStream;
	
	public Map<String, String> GetConfiguration() throws IOException
	{
		Map<String, String> map = new HashMap<>(); 
		
		try {
			Properties prop = new Properties();
			String propFileName = "application.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 

 
			// get the property value and print it out
			
			map.put("loginFtp", prop.getProperty("loginFtp"));
			map.put("passwordFtp", prop.getProperty("passwordFtp"));
			map.put("loginEmail", prop.getProperty("loginEmail"));
			map.put("passwordEmail", prop.getProperty("passwordEmail"));
	
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return map;
	}
	
	public String generate() {
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
	    String pass = "";
	    for(int x=0;x<7;x++)   {
	       int i = (int)Math.floor(Math.random() * chars.length() -1); // Si tu supprimes des lettres tu diminues ce nb
	       pass += chars.charAt(i);
	    }
	    System.out.println(pass);
	    return pass;
}

}
