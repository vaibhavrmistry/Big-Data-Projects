package com.neu.bigdata;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

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
import com.neu.bigdata.model.Zone;




/**
 * Handles requests for the application home page.
 */
@Controller


public class HomeController {
	
	@Autowired
	NYCDaoImpl nycDaoImpl;
	
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
	
	@RequestMapping(value = "/getscore.htm", method = RequestMethod.GET)
	public @ResponseBody Double getScore(HttpServletResponse response) throws UnknownHostException {
		
		Double orginLat = 40.6;
		Double orginLong = 70.872;
		
		Double pickupLat = 40.755;
		Double pickupLong = 70.456;
		
		String zoneId = getZone(pickupLat, pickupLong);
		
		//Get the density and tip of zone
		Zone zone = nycDaoImpl.selectZone(zoneId);
		Integer density = zone.getDensity();
		Double avgTip = zone.getAvgTip();
		
		//Get the recommended zones
		List<String> mahoutRecommendedZones = getRecommendedZones();
		
		
		return 100.0;
		
	}
	
	
	public List<String> getRecommendedZones(){
		List<Recommendation> recommendList = null;
		HashSet<String> zoneSet = new HashSet<String>();
	  try {
		recommendList = 	nycDaoImpl.getMahoutRecommendation();
	  } catch (UnknownHostException e) {
		e.printStackTrace();
	  }
	  
	  for(Recommendation r : recommendList){
		  zoneSet.add(getZone(r.getLatitude(), r.getLongitude()));
	  }
	  
	  return new ArrayList<String>(zoneSet);
	}

	
	
	public String getZone(double pickupLat, double pickupLong) {

		if (pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -74
				&& pickupLong < -73.95)
			return "1B";
		else if (pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "1C";
		else if (pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "1D";
		else if (pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "2A";
		else if (pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "2B";
		else if (pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.85
				&& pickupLong < -73.8)
			return "2C";
		else if (pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.85
				&& pickupLong < -73.8)
			return "2D";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -74.05
				&& pickupLong < -74)
			return "3B";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -74
				&& pickupLong < -73.95)
			return "4A";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -74
				&& pickupLong < -73.95)
			return "4B";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "4C";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "4D";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "5A";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "5B";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.85
				&& pickupLong < -73.8)
			return "5C";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.85
				&& pickupLong < -73.8)
			return "5D";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.8
				&& pickupLong < -73.75)
			return "6A";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.8
				&& pickupLong < -73.75)
			return "6B";
		else if (pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.75
				&& pickupLong < -73.7)
			return "6C";
		else if (pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.75
				&& pickupLong < -73.7)
			return "6D";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -74
				&& pickupLong < -73.95)
			return "7A";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74
				&& pickupLong < -73.95)
			return "7B";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "7C";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "7D";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "8A";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.9
				&& pickupLong < -73.85)
			return "8B";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.85
				&& pickupLong < -73.8)
			return "8D";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.8
				&& pickupLong < -73.75)
			return "9A";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.8
				&& pickupLong < -73.75)
			return "9B";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.75
				&& pickupLong < -73.7)
			return "9C";
		else if (pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.75
				&& pickupLong < -73.7)
			return "9D";
		else if (pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74
				&& pickupLong < -73.95)
			return "10A";
		else if (pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -73.95
				&& pickupLong < -73.9)
			return "10D";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.1
				&& pickupLong < -74.05)
			return "11A";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.05
				&& pickupLong < -74)
			return "11D";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.2
				&& pickupLong < -74.15)
			return "12A";
		else if (pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74.2
				&& pickupLong < -74.15)
			return "12B";
		else if (pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74.15
				&& pickupLong < -74.1)
			return "12C";
		else if (pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.15
				&& pickupLong < -74.1)
			return "12D";
		else if (pickupLat > 40.5 && pickupLat < 40.55 && pickupLong > -74.25
				&& pickupLong < -74.2)
			return "13A";

		return "ND";

	}
	
	
}
