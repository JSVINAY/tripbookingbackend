package com.training.tripbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.Passenger;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
	
	public Passenger findByGovId(String govId);

}
