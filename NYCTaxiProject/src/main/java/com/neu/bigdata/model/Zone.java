package com.neu.bigdata.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
@Entity("zone")
public class Zone {
	
		@Id
		@Property("id")
		private ObjectId id;

		@Property("zoneId")
		private String zoneId;

		@Property("density")
		private Integer density;
		
		@Property("avgTip")
		private Double avgTip;

		public Zone() {

		}

		public ObjectId getId() {
			return id;
		}

		public void setId(ObjectId id) {
			this.id = id;
		}

		public String getZoneId() {
			return zoneId;
		}

		public void setZoneId(String zoneId) {
			this.zoneId = zoneId;
		}

		public Integer getDensity() {
			return density;
		}

		public void setDensity(Integer density) {
			this.density = density;
		}

		public Double getAvgTip() {
			return avgTip;
		}

		public void setAvgTip(Double avgTip) {
			this.avgTip = avgTip;
		}

		
}
