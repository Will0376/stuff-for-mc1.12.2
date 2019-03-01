package ru.will0376.Willmod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UpdLogLoading {
	public static JSONObject LoadJson(String string) {
		
		String js = readToString(string.toString());
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(js);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) obj;
		
		return jsonObject;
		
	}
	 public static String readToString(String targetURL) 
		{
		    StringBuilder stringBuilder = new StringBuilder();
		    String inputLine;
				try {
					URL url = new URL(targetURL);
					 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
						while ((inputLine = bufferedReader.readLine()) != null)
						{
						    stringBuilder.append(inputLine);
						    stringBuilder.append(System.lineSeparator());
						}
						bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    return stringBuilder.toString().trim();
		}
}
