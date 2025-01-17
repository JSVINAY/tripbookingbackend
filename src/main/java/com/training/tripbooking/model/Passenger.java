package com.training.tripbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private int age;
	private String gender;
	private String foodType;
	private String govId;
	private String govIdType;

	// Default constructor
	public Passenger() {
		// No-op
	}

	// Constructor with all fields

	public Passenger(Long id, String name, int age, String gender, String foodType, String govId, String govIdType) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.foodType = foodType;
		this.govId = govId;
		this.govIdType = govIdType;
	}

	// Getters and Setters

		public Long getId() {
			return id;
		}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public String getGovId() {
		return govId;
	}

	public void setGovId(String govId) {
		this.govId = govId;
	}

	public String getGovIdType() {
		return govIdType;
	}

	public void setGovIdType(String govIdType) {
		this.govIdType = govIdType;
	}

}
