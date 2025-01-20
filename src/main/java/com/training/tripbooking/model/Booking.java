package com.training.tripbooking.model;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	private String destination;

	@OneToMany(cascade=CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "bookingId")
	private List<Passenger> passengers;

	@Enumerated(EnumType.STRING)
	private StayType stayType;

	private String pickupLocation;
	private String dropLocation;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
	private UserEntity user;
	
	

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", destination=" + destination + ", passengers=" + passengers
				+ ", stayType=" + stayType + ", pickupLocation=" + pickupLocation + ", dropLocation=" + dropLocation
				+ ", user=" + user + "]";
	}

	public Booking(Long bookingId, String destination, List<Passenger> passengers, StayType stayType,
			String pickupLocation, String dropLocation, UserEntity user) {
		this.bookingId = bookingId;
		this.destination = destination;
		this.passengers = passengers;
		this.stayType = stayType;
		this.pickupLocation = pickupLocation;
		this.dropLocation = dropLocation;
		this.user = user;
	}

	public Booking() {
		// Default constructor
	}

	// Getters and Setters

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public StayType getStayType() {
		return stayType;
	}

	public void setStayType(StayType stayType) {
		this.stayType = stayType;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
