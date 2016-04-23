package com.neu.bigdata.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

@Entity("user")
public class User {
	
		@Id
		@Property("id")
		private ObjectId id;

		@Property("medallionId")
		private String medallionId;

		@Embedded
		private List<Recommendation> recommendationsList;
		
		@Embedded
		private List<Visited> visitedList;
		
		public User() {
			recommendationsList = new ArrayList<Recommendation>();
			visitedList = new ArrayList<Visited>();
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

		public List<Recommendation> getRecommendationsList() {
			return recommendationsList;
		}

		public void setRecommendationsList(List<Recommendation> recommendationsList) {
			this.recommendationsList = recommendationsList;
		}

		public List<Visited> getVisitedList() {
			return visitedList;
		}

		public void setVisitedList(List<Visited> visitedList) {
			this.visitedList = visitedList;
		}


}

