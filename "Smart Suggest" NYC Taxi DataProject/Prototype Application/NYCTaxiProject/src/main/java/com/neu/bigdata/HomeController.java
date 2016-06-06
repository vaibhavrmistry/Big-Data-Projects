package com.neu.bigdata;

import java.awt.PageAttributes.OriginType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.bigdata.dao.NYCDaoImpl;
import com.neu.bigdata.model.Recommendation;
import com.neu.bigdata.model.Visited;
import com.neu.bigdata.model.Zone;







/**
 * Handles requests for the application home page.
 */
@Controller


public class HomeController {
	
	@Autowired
	NYCDaoImpl nycDaoImpl;
	
	Double orginLat;
	Double orginLong;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/recordDriverLocation.htm", method = RequestMethod.GET)
	public @ResponseBody String recordDriverLocation(HttpServletRequest request) throws ClientProtocolException, IOException, JSONException {
		
		orginLat = Double.valueOf(request.getParameter("driverLat"));
		orginLong = Double.valueOf(request.getParameter("driverLng"));
	
		return "";
	}
	
	@RequestMapping(value = "/getScore.htm", method = RequestMethod.GET)
	public @ResponseBody Double getScore(HttpServletRequest request) throws ClientProtocolException, IOException, JSONException {
		
		
		Double pickupLat = Double.valueOf(request.getParameter("pickupLat"));
		Double pickupLong = Double.valueOf(request.getParameter("pickupLng"));
		
		System.out.println("Pickup Lat: "+pickupLat + ", Pickup Long: "+pickupLong);
			
		Double dropLat = Double.valueOf(request.getParameter("dropLat"));
		Double dropLong = Double.valueOf(request.getParameter("dropLng"));
		
		System.out.println("Drop Lat "+ dropLat + ", Drop Long "+ dropLong);
			
		String zoneId = getZone(pickupLat, pickupLong);
		if(zoneId.equals("ND")){
			return 0.0;
			}
		//Get the density and avg tip of zone
		Zone zone = nycDaoImpl.selectZone(zoneId);
		Integer density = zone.getDensity();
		Double avgTip = zone.getAvgTip();
		System.out.println("Pickup Density: " + density);
		System.out.println("AvgTip: " + avgTip);
		
		//Get the recommended zones
		HashMap<String,Double> mahoutRecommendedZones = getRecommendedZones();
		
		//Get the visited 
		HashMap<String,Double> visitedZones = getVistedZones();
		
		//get the distance between pick up and current
		Double disFromCurrToPickUp = getDistanceFromCurrentToPickUp(orginLat,orginLong,pickupLat,pickupLong);
		System.out.println("distance from pickup: "+disFromCurrToPickUp);
		//get the drop off zone
		String dropZoneId = getZone(dropLat, dropLong);
		Zone dropZone = nycDaoImpl.selectZone(dropZoneId);
		int dropZoneDensity = dropZone.getDensity();
		System.out.println("drop zone density: "+ dropZoneDensity);
		
		
		
		//Get the scores
		int  densityScore = getDensityScore(density);
		int  avgTipScore = getAvgTipScore(avgTip);
		int disFromCurrToPickUpScore =  getDisFromCurrToPickUpScore(disFromCurrToPickUp);
		int dropZoneScore =  getDensityScore(dropZoneDensity);

		int prefScore=0;
		if(mahoutRecommendedZones.containsKey(zoneId)){
			Double pref = mahoutRecommendedZones.get(zoneId);
			if( pref > 0 && pref < 30000) prefScore = 1;
			else if ( pref >= 30000 && pref < 35000) prefScore = 2;
			else if ( pref >= 35000 && pref < 40000) prefScore = 3;
			else if ( pref >= 40000 && pref < 45000) prefScore = 4;
			else if ( pref >= 45000 && pref < 50000) prefScore = 5;		
			else if ( pref >= 50000 && pref < 55000) prefScore = 6;
			else if ( pref >= 55000 && pref < 60000) prefScore = 7;
			else if ( pref >= 60000 && pref < 65000) prefScore = 8;
			else if ( pref >= 65000 && pref < 70000) prefScore = 9;
			else if ( pref >= 70000) prefScore = 10;
			else prefScore = -1;
			
		}else if(visitedZones.containsKey(zoneId)){
			Double pref = visitedZones.get(zoneId);
			if( pref > 0 && pref < 2) prefScore = 1;
			else if ( pref >= 2 && pref < 3) prefScore = 2;
			else if ( pref >= 3 && pref < 4) prefScore = 3;
			else if ( pref >= 4 && pref < 5) prefScore = 4;
			else if ( pref >= 5 && pref < 6) prefScore = 5;		
			else if ( pref >= 6 && pref < 7) prefScore = 6;
			else if ( pref >= 7 && pref < 8) prefScore = 7;
			else if ( pref >= 8 && pref < 9) prefScore = 8;
			else if ( pref >= 9 && pref < 11) prefScore = 9;
			else if ( pref >= 11) prefScore = 10;
			else prefScore = -1;
			
		}else {
			System.out.println("Niether recommended nor visited");
		}
		
		Double w1 = 0.1, w2 = 0.2, w3 = 0.2, w4 = 0.4, w5  = 0.1;
		Double score =  w1 * densityScore + w2 * avgTipScore + 
				w3 * disFromCurrToPickUpScore + w4 * prefScore + w5 * dropZoneScore;
		
		System.out.println("Density Score: " + densityScore);
		System.out.println("AvgTip Score: " + avgTipScore);
		System.out.println("DistFromCurrToPickUpScore: " + disFromCurrToPickUpScore);
		System.out.println("PrefScore: " + prefScore);
		System.out.println("DropZoneScore: " + dropZoneScore);
		
		System.out.println("Score "+score);
		System.out.println("----------------------------------");
		return score;
		
	}
	
	public int getDensityScore(Integer density){
		if( density > 0 && density < 50) return 1;
		else if ( density >= 50 && density < 200) return 2;
		else if ( density >= 200 && density < 500) return 3;
		else if ( density >= 500 && density < 1000) return 4;
		else if ( density >= 1000 && density < 5000) return 5;		
		else if ( density >= 5000 && density < 50000) return 6;
		else if ( density >= 50000 && density < 100000) return 7;
		else if ( density >= 100000 && density < 200000) return 8;
		else if ( density >= 200000 && density < 300000) return 9;
		else if ( density >= 300000) return 10;
		else return 0;
	}
	
	
	public int getAvgTipScore(Double avgTip){
		if( avgTip > 0 && avgTip < 1.07) return 1;
		else if ( avgTip >= 1.07 && avgTip < 1.13) return 2;
		else if ( avgTip >= 1.13 && avgTip < 1.25) return 3;
		else if ( avgTip >= 1.25 && avgTip < 1.365) return 4;
		else if ( avgTip >= 1.365 && avgTip < 1.516) return 5;		
		else if ( avgTip >= 1.516 && avgTip < 1.65) return 6;
		else if ( avgTip >= 1.65 && avgTip < 2.00) return 7;
		else if ( avgTip >= 2.00 && avgTip < 2.77) return 8;
		else if ( avgTip >= 2.77 && avgTip < 3.92) return 9;
		else if ( avgTip >= 3.92 && avgTip < 4.9) return 10;
		else if (avgTip >=4.9) return 11;
		else return 0;
	}
	
	public int getDisFromCurrToPickUpScore(Double  distance){
		if( distance > 0 && distance < 2) return 10;
		else if ( distance >= 2 && distance < 5) return 9;
		else if ( distance >= 5 && distance < 7) return 8;
		else if ( distance >= 7 && distance < 15) return 7;
		else if ( distance >= 15 && distance < 20) return 6;		
		else if ( distance >= 20 && distance < 35) return 5;
		else if ( distance >= 35 && distance < 50) return 4;
		else if ( distance >= 50 && distance < 70) return 3;
		else if ( distance >= 70 && distance < 100) return 2;
		else if ( distance >= 100 ) return 1;
		else return -1;
	}
	
	
	public HashMap<String,Double> getVistedZones(){
		List<Visited> visitedList = null;
		HashMap<String,Double> visitedMap = new HashMap<String,Double>();
	  try {
		  visitedList = nycDaoImpl.getVisited();
	  } catch (UnknownHostException e) {
		e.printStackTrace();
	  }
	  
	  for(Visited v : visitedList){
		  String zoneId = getZone(v.getLatitude(), v.getLongitude());
		  if(visitedMap.containsKey(zoneId)){
			  Double tip = visitedMap.get(zoneId);
			  if(v.getTip() > tip){
				  visitedMap.put(zoneId, v.getTip());
			  }
		  }else{
			  visitedMap.put(zoneId, v.getTip());
		  }
	  }
	  
	  return visitedMap;
	}
	
	
	public HashMap<String,Double> getRecommendedZones(){
		List<Recommendation> recommendList = null;
		HashMap<String,Double> zoneMap = new HashMap<String,Double>();
	  try {
		recommendList = nycDaoImpl.getMahoutRecommendation();
	  } catch (UnknownHostException e) {
		e.printStackTrace();
	  }
	  
	  for(Recommendation r : recommendList){
		  String zoneId = getZone(r.getLatitude(), r.getLongitude());
		  if(zoneMap.containsKey(zoneId)){
			  Double pref = zoneMap.get(zoneId);
			  if(r.getPreference() > pref){
				  zoneMap.put(zoneId, r.getPreference());
			  }
		  }else{
		  zoneMap.put(zoneId, r.getPreference());
		  }
	  }
	  
	  return zoneMap;
	}

	
	
	public String getZone(double pickupLat, double pickupLong) {

		if (pickupLat >= 40.8 && pickupLat <= 40.85 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "1B";
		else if (pickupLat >= 40.8 && pickupLat <= 40.85 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "1C";
		else if (pickupLat >= 40.85 && pickupLat <= 40.9 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "1D";
		else if (pickupLat >= 40.85 && pickupLat <= 40.9 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "2A";
		else if (pickupLat >= 40.8 && pickupLat <= 40.85 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "2B";
		else if (pickupLat >= 40.8 && pickupLat <= 40.85 && pickupLong >= -73.85
				&& pickupLong <= -73.8)
			return "2C";
		else if (pickupLat >= 40.85 && pickupLat <= 40.9 && pickupLong >= -73.85
				&& pickupLong <= -73.8)
			return "2D";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -74.05
				&& pickupLong <= -74)
			return "3B";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "4A";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "4B";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "4C";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "4D";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "5A";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "5B";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -73.85
				&& pickupLong <= -73.8)
			return "5C";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -73.85
				&& pickupLong <= -73.8)
			return "5D";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -73.8
				&& pickupLong <= -73.75)
			return "6A";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -73.8
				&& pickupLong <= -73.75)
			return "6B";
		else if (pickupLat >= 40.7 && pickupLat <= 40.75 && pickupLong >= -73.75
				&& pickupLong <= -73.7)
			return "6C";
		else if (pickupLat >= 40.75 && pickupLat <= 40.8 && pickupLong >= -73.75
				&& pickupLong <= -73.7)
			return "6D";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "7A";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "7B";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "7C";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "7D";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "8A";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -73.9
				&& pickupLong <= -73.85)
			return "8B";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -73.85
				&& pickupLong <= -73.8)
			return "8D";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -73.8
				&& pickupLong <= -73.75)
			return "9A";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -73.8
				&& pickupLong <= -73.75)
			return "9B";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -73.75
				&& pickupLong <= -73.7)
			return "9C";
		else if (pickupLat >= 40.65 && pickupLat <= 40.7 && pickupLong >= -73.75
				&& pickupLong <= -73.7)
			return "9D";
		else if (pickupLat >= 40.55 && pickupLat <= 40.6 && pickupLong >= -74
				&& pickupLong <= -73.95)
			return "10A";
		else if (pickupLat >= 40.55 && pickupLat <= 40.6 && pickupLong >= -73.95
				&& pickupLong <= -73.9)
			return "10D";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -74.1
				&& pickupLong <= -74.05)
			return "11A";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -74.05
				&& pickupLong <= -74)
			return "11D";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -74.2
				&& pickupLong <= -74.15)
			return "12A";
		else if (pickupLat >= 40.55 && pickupLat <= 40.6 && pickupLong >= -74.2
				&& pickupLong <= -74.15)
			return "12B";
		else if (pickupLat >= 40.55 && pickupLat <= 40.6 && pickupLong >= -74.15
				&& pickupLong <= -74.1)
			return "12C";
		else if (pickupLat >= 40.6 && pickupLat <= 40.65 && pickupLong >= -74.15
				&& pickupLong <= -74.1)
			return "12D";
		else if (pickupLat >= 40.5 && pickupLat <= 40.55 && pickupLong >= -74.25
				&& pickupLong <= -74.2)
			return "13A";

		return "ND";

	}
	
	public Double getDistanceFromCurrentToPickUp(Double orginLat, Double originLong, Double pickupLat, Double pickupLong)  {
		String dist	= "0";
	try{	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+ orginLat +"," + originLong + "&destinations=" + pickupLat +"," + pickupLong + "&key=AIzaSyB_7sbJNUCCpAvwO8BTJpzCJhJeK_y3ZeY");
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));		
		
		String jsonText = readAll(rd);
	    JSONObject jsonObject = new JSONObject(jsonText);
	    
	    JSONArray jsonArray = (JSONArray) jsonObject.get("rows");
	    JSONObject elementObject =  jsonArray.getJSONObject(0);
	    JSONArray elementJsonArray  = (JSONArray) elementObject.get("elements");
	    JSONObject distanceObj = elementJsonArray.getJSONObject(0);
	    JSONObject distance = (JSONObject)distanceObj.get("distance");

	     dist = distance.getString("text");
	    	
		dist = dist.replace(" mi", "");
	}catch(Exception e){
		
	}
		
		return Double.valueOf(dist);
	}
	
	
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
		HomeController hc = new HomeController();
	//	System.out.println(hc.getDistanceFromCurrentToPickUp(40.6655101, -73.89188969999998, 41.6655101, -73.89188969999998));
		System.out.println(hc.getDistanceFromCurrentToPickUp(40.6655101, -73.89188969999998, 41.6655101, 73.89188969999998));

//		System.out.println(hc.getScore(null));
	}
	
	
}
