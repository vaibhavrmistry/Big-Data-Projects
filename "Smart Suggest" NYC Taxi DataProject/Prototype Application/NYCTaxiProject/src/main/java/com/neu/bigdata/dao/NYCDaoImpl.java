package com.neu.bigdata.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.neu.bigdata.model.Recommendation;
import com.neu.bigdata.model.User;
import com.neu.bigdata.model.Visited;
import com.neu.bigdata.model.Zone;


@Component
public class NYCDaoImpl extends NYCDao{
	public static String dbName = new String("nyc");
	public static String packageName = "com.neu.bigdata.model";
	
	
	public void insertZoneDensity(Zone zone){
		Datastore datastore = datastoreConn(dbName, packageName);
		datastore.save(zone);
		
	}
	
	public void insertVisited(Visited visited){
		Datastore datastore = datastoreConn(dbName, packageName);
		datastore.save(visited);
		
	}
	
	public void insertUser(){
		Datastore datastore = datastoreConn(dbName, packageName);
		//First create a user and add
		User user = new User();
		user.setMedallionId("6935FB4A876D521542AAB76210CEB4C2");

	//	datastore.save(user);
		
		Recommendation recommend1 = new Recommendation();
		recommend1.setLatitude(40.64);
		recommend1.setLongitude(-73.79);
		recommend1.setPreference(76900.0);
		user.getRecommendationsList().add(recommend1);
		
		Recommendation recommend2 = new Recommendation();
		recommend2.setLatitude(40.7);
		recommend2.setLongitude(-73.97);
		recommend2.setPreference(66807.0);
		user.getRecommendationsList().add(recommend2);
		
		Recommendation recommend3 = new Recommendation();
		recommend3.setLatitude(40.74);
		recommend3.setLongitude(-73.92);
		recommend3.setPreference(65099.0);
		user.getRecommendationsList().add(recommend3);
		
		Recommendation recommend4 = new Recommendation();
		recommend4.setLatitude(40.68);
		recommend4.setLongitude(-74.0);
		recommend4.setPreference(43773.0);
		user.getRecommendationsList().add(recommend4);
		
		Recommendation recommend5 = new Recommendation();
		recommend5.setLatitude(40.68);
		recommend5.setLongitude(-73.97);
		recommend5.setPreference(43620.0);
		user.getRecommendationsList().add(recommend5);
		
		Recommendation recommend6 = new Recommendation();
		recommend6.setLatitude(40.71);
		recommend6.setLongitude(-73.99);
		recommend6.setPreference(43228.0);
		user.getRecommendationsList().add(recommend6);
		
		Recommendation recommend7 = new Recommendation();
		recommend7.setLatitude(40.69);
		recommend7.setLongitude(-74.0);
		recommend7.setPreference(37164.0);
		user.getRecommendationsList().add(recommend7);

		Recommendation recommend8 = new Recommendation();
		recommend8.setLatitude(40.71);
		recommend8.setLongitude(-73.94);
		recommend8.setPreference(36853.0);
		user.getRecommendationsList().add(recommend8);
		
		Recommendation recommend9 = new Recommendation();
		recommend9.setLatitude(40.68);
		recommend9.setLongitude(-73.99);
		recommend9.setPreference(34067.0);
		user.getRecommendationsList().add(recommend9);
		
		Recommendation recommend10 = new Recommendation();
		recommend10.setLatitude(40.67);
		recommend10.setLongitude(-73.99);
		recommend10.setPreference(32792.0);
		user.getRecommendationsList().add(recommend10);
		readVisitedFromCSV(user);
		datastore.save(user);
		
		
		
	}
	public void readVisitedFromCSV(User user){
		String csvFile = "/Users/jigarshah/Downloads/FinalProject/mahoutInputFile1.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			int count = 0;
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();					
			while ((line = br.readLine()) != null && count < 294) {
				String[] visitedString = line.split(cvsSplitBy);
				Visited visited = new Visited();
				String[] latAndLog= visitedString[1].split(":");
				visited.setLatitude(Double.valueOf(latAndLog[0]));
				visited.setLongitude(Double.valueOf(latAndLog[1]));
				visited.setTip(Double.valueOf(visitedString[2]));
			//	insertVisited(visited);
				user.getVisitedList().add(visited);
				count++;
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void readZoneFromCSV(){
		String csvFile = "/Users/jigarshah/Downloads/FinalProject/ZoneAndDensity.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();					
			while ((line = br.readLine()) != null) {
				String[] zoneString = line.split(cvsSplitBy);
				Zone zone = new Zone();
				zone.setZoneId(zoneString[0]);
				zone.setDensity(Integer.valueOf(zoneString[1]));
				zone.setAvgTip(Double.valueOf(zoneString[2]));
				insertZoneDensity(zone);
				System.out.println("Zone [zone= " + zoneString[0] 
	                                 + " , density=" + zoneString[1] + " , avgTip=" + zoneString[2] +"]");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public Zone selectZone(String zoneId) throws UnknownHostException {
		Datastore datastore = datastoreConn(dbName, packageName);
		Query<Zone> query = datastore.createQuery(Zone.class);
		query.criteria("zoneId").equal(zoneId);
		List<Zone> zoneData = query.asList();
		return zoneData.get(0);
	}
	
	public List<Recommendation> getMahoutRecommendation() throws UnknownHostException {
		Datastore datastore = datastoreConn(dbName, packageName);
		Query<User> query = datastore.createQuery(User.class);
		
		List<User> userList = query.asList();
		
		List<Recommendation> recommendationList = userList.get(0).getRecommendationsList();
		return recommendationList;
	}
	
	public List<Visited> getVisted() throws UnknownHostException {
		Datastore datastore = datastoreConn(dbName, packageName);
		Query<User> query = datastore.createQuery(User.class);
		
		List<User> userList = query.asList();
		
		List<Visited> visitedList = userList.get(0).getVisitedList();
		return visitedList;
	}
	
	public List<Visited> getVisited() throws UnknownHostException {
		Datastore datastore = datastoreConn(dbName, packageName);
		Query<User> query = datastore.createQuery(User.class);
		
		List<User> userList = query.asList();
		
		List<Visited> visitedList = userList.get(0).getVisitedList();
		return visitedList;
	}

	public static void main(String[] args) throws UnknownHostException {
//		NYCDaoImpl nycDaoImpl = new NYCDaoImpl();
//		Zone zd = new Zone();
//		zd.setZoneId("1A");
//		zd.setDensity(500);
//		nycDaoImpl.insertZoneDensity(zd);
		
//		NYCDaoImpl nycDaoImpl = new NYCDaoImpl();
//		//nycDaoImpl.insertUser();
//		nycDaoImpl.readZoneFromCSV();
		
//		NYCDaoImpl nycDaoImpl = new NYCDaoImpl();
//		Zone zone = nycDaoImpl.selectZone("2A");
//		System.out.println(zone.getDensity());
		
//		NYCDaoImpl nycDaoImpl = new NYCDaoImpl();
//		List<Recommendation> recommeList = nycDaoImpl.getMahoutRecommendation();
//		System.out.println(recommeList.size());
		
//		NYCDaoImpl nycDaoImpl = new NYCDaoImpl();
//		List<Visited> visitedList = nycDaoImpl.getVisited();
//		System.out.println(visitedList.size());
		
	}

}
