package com.training.tripbooking.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.repositories.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class); // Initialize logger

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        logger.info("Creating booking: {}", booking);

        // TODO: Check for user registration
        // TODO: Add Passenger

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking created successfully with ID: {}", savedBooking.getBookingId());

        return savedBooking;
    }

    @Override
    public List<Booking> getAllBookings() {
        logger.info("Fetching all bookings");

        List<Booking> bookings = bookingRepository.findAll();
        logger.info("Retrieved {} bookings", bookings.size());

        return bookings;
    }

    @Override
    public Booking getBookingById(Long id) {
        logger.info("Fetching booking with ID: {}", id);

        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            logger.info("Found booking with ID: {}", id);
        } else {
            logger.warn("Booking with ID {} not found", id);
        }

        return booking;
    }

    @Override
    public void deleteBooking(Long id) {
        logger.info("Deleting booking with ID: {}", id);

        bookingRepository.deleteById(id);
        logger.info("Successfully deleted booking with ID: {}", id);
    }
}
