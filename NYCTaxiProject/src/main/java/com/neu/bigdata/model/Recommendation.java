package com.neu.bigdata.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
@Entity("recommendation")
public class Recommendation implements Serializable {
	
		@Id
		@Property("id")
		private ObjectId id;

		@Property("medallionId")
		private String medallionId;

		@Property("latitude")
		private Double latitude;
		
		@Property("longitude")
		private Double longitude;
		
		@Property("preference")
		private Double preference;


		public Recommendation() {

		}


		public ObjectId getId() {
			return id;
		}


		public void setId(ObjectId id) {
			this.id = id;
		}


		public String getMedallionId() {
			return medallionId;
		}


		public void setMedallionId(String medallionId) {
			this.medallionId = medallionId;
		}


		public Double getLatitude() {
			return latitude;
		}


		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}


		public Double getLongitude() {
			return longitude;
		}


		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}


		public Double getPreference() {
			return preference;
		}


		public void setPreference(Double preference) {
			this.preference = preference;
		}

		
}
