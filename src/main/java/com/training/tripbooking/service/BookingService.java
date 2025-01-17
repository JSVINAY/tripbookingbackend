package com.training.tripbooking.service;

import java.util.List;

import com.training.tripbooking.model.Booking;

public interface BookingService {
	Booking createBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
}
