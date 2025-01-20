package com.training.tripbooking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class); // Initialize logger

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping("/insert")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        logger.info("Received request to create a new booking: {}", booking);
        Booking createdBooking = bookingService.createBooking(booking);
        logger.info("Successfully created booking with ID: {}", createdBooking.getBookingId());
        return ResponseEntity.ok(createdBooking);
    }

    // Get all bookings
    @GetMapping("/all/packagebooking")
    public ResponseEntity<List<Booking>> getAllBookings() {
        logger.info("Fetching all bookings");
        List<Booking> bookings = bookingService.getAllBookings();
        logger.info("Retrieved {} bookings", bookings.size());
        return ResponseEntity.ok(bookings);
    }

    // Get a specific booking by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        logger.info("Fetching booking with ID: {}", id);
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            logger.info("Found booking with ID: {}", id);
            return ResponseEntity.ok(booking);
        } else {
            logger.warn("Booking with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a booking by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        logger.info("Received request to delete booking with ID: {}", id);
        bookingService.deleteBooking(id);
        logger.info("Successfully deleted booking with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
