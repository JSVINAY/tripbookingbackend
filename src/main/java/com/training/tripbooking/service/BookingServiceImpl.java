package com.training.tripbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.repositories.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	@Transactional
	public Booking createBooking(Booking booking) {
		//TODO Check of user registration 
		//TODO Add Passenger
		
		return bookingRepository.save(booking);
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteBooking(Long id) {
		bookingRepository.deleteById(id);
	}

}
