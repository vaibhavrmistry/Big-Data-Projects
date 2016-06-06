package com.neu.bigdata.dao;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class NYCDao {

	public static MongoClient mongoClient;

	public static MongoClient connection() {

		try {
			if (mongoClient == null) {
				mongoClient = new MongoClient("localhost", 27017);
			}
			//System.out.println("Successfully Connected");
		} catch (UnknownHostException e) {
			System.out.println("Unable to connect to Mongodb");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mongoClient;
	}
	
	public static Datastore datastoreConn(String dbName, String packageName){
		MongoClient mongoClient =connection();
		Morphia morphia = new Morphia();
		morphia.mapPackage(packageName);
		Datastore datastore = morphia.createDatastore(mongoClient, dbName);
		return datastore;
	}



}
