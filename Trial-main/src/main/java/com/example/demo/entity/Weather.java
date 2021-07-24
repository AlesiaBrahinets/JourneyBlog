package com.example.demo.entity;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Weather {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JoinColumn(name="weatherFK")
	private Long id;
	
	@Column(nullable = false, updatable = false)
	private Double temperature;

	@Column(nullable = false, updatable = false)
	private Double humidity;
	
	@Column(nullable = false, updatable = false)
	private Double windSpeed;
	
	@Column(nullable = false, updatable = false)
	private Double windDirection;
	
	
	private static Weather weather;
	
	private Weather () {}
	
	public static Weather getInstance(String location) {

	      if(weather == null) {
	         weather = new Weather();
	         
	      }
	      weather = weather.getWeather(location);
	       // returns the singleton object
	       return weather;
	   }
	
	private Weather getWeather(String location) {
		String API_KEY = "244ba6844919779cd32005cba85a80c5";
		String LOCATION = location;
		String _URL = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION +"&appid=" + API_KEY + "&units=metric";
		
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(_URL);
			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			
			Map<String, Object> map = jsonToMap(result.toString());
			Map<String, Object> mainMap = jsonToMap(map.get("main").toString());
			Map<String, Object> windMap = jsonToMap(map.get("wind").toString());
			
			weather.temperature = (Double) mainMap.get("temp"); 
			weather.humidity = (Double) mainMap.get("humidity");
			weather.windSpeed = (Double) windMap.get("speed");
			weather.windDirection = (Double)windMap.get("deg");			
			
		}catch (Exception ex) {
			System.out.println("Error " + ex.getMessage());
		}
		
		return weather;
	}
	
	public Map<String, Object> jsonToMap(String str){
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
		return map;
	}

}
