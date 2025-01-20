package com.training.tripbooking.service;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.model.Passenger;
import com.training.tripbooking.model.StayType;
import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private UserEntity user;
    private List<Passenger> passengers;
    private StayType stayType;

    @BeforeEach
    void setUp() {
        // Create UserEntity
        user = new UserEntity();
        user.setId(1L);
        user.setUsername("TestUser");

        // Create Passengers
        passengers = new ArrayList<>();
        Passenger passenger = new Passenger();
        passenger.setName("Passenger1");
        passengers.add(passenger);

        // Set StayType
        stayType = StayType.HOTEL;

        // Create Booking
        booking = new Booking();
        booking.setBookingId(1L);
        booking.setDestination("Paris");
        booking.setPassengers(passengers);
        booking.setStayType(stayType);
        booking.setPickupLocation("Airport");
        booking.setDropLocation("Hotel");
        booking.setUser(user);
    }

    @Test
    void testCreateBooking() {
        // Arrange
        when(bookingRepository.save(booking)).thenReturn(booking);

        // Act
        Booking savedBooking = bookingService.createBooking(booking);

        // Assert
        assertNotNull(savedBooking);
        assertEquals(1L, savedBooking.getBookingId());
        assertEquals("Paris", savedBooking.getDestination());
        assertEquals("TestUser", savedBooking.getUser().getUsername());
        assertEquals(1, savedBooking.getPassengers().size());
        assertEquals(StayType.HOTEL, savedBooking.getStayType());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testGetAllBookings() {
        // Arrange
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findAll()).thenReturn(bookings);

        // Act
        List<Booking> result = bookingService.getAllBookings();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestUser", result.get(0).getUser().getUsername());
        assertEquals("Paris", result.get(0).getDestination());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingByIdFound() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Act
        Booking result = bookingService.getBookingById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
        assertEquals("Paris", result.getDestination());
        assertEquals("TestUser", result.getUser().getUsername());
        assertEquals(1, result.getPassengers().size());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingByIdNotFound() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Booking result = bookingService.getBookingById(1L);

        // Assert
        assertNull(result);
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteBooking() {
        // Act
        bookingService.deleteBooking(1L);

        // Assert
        verify(bookingRepository, times(1)).deleteById(1L);
    }
}