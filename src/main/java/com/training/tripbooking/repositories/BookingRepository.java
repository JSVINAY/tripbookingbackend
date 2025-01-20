package com.training.tripbooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.model.UserEntity;




@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUser(UserEntity user);
}
