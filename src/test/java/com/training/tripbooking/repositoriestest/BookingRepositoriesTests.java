package com.training.tripbooking.repositoriestest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.training.tripbooking.model.Booking;
import com.training.tripbooking.model.Passenger;
import com.training.tripbooking.model.StayType;
import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.repositories.BookingRepository;

@ExtendWith(MockitoExtension.class)
public class BookingRepositoriesTests {

    @Mock
    private BookingRepository bookingRepository; // Mock BookingRepository

    @Mock
    private UserEntity user; // Mock UserEntity (since it is part of the Booking)
    
    private Booking booking;

    @BeforeEach
    public void setUp() {
        // Initialize the Booking entity with mock data
        Passenger passenger1 = new Passenger("John", "Doe");
        Passenger passenger2 = new Passenger("Jane", "Doe");

        booking = new Booking();
        booking.setBookingId(1L);
        booking.setDestination("Varanasi");
        booking.setStayType(StayType.HOTEL);
        booking.setPickupLocation("Airport");
        booking.setDropLocation("Hotel");
        booking.setUser(user); // Set mocked user
        booking.setPassengers(Arrays.asList(passenger1, passenger2)); // List of passengers
    }

    @Test
    public void testSaveBooking() {
        // Arrange
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking); // Mock save method

        // Act
        Booking savedBooking = bookingRepository.save(booking); // Call the save method

        // Assert
        assertNotNull(savedBooking);
        assertEquals("Varanasi", savedBooking.getDestination());
        assertEquals(StayType.HOTEL, savedBooking.getStayType());
        assertEquals("Airport", savedBooking.getPickupLocation());
        assertEquals("Hotel", savedBooking.getDropLocation());
        
        // Verify interaction with the mock
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testFindById() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking)); // Mock findById method

        // Act
        Optional<Booking> foundBooking = bookingRepository.findById(1L);

        // Assert
        assertTrue(foundBooking.isPresent());
        assertEquals("Varanasi", foundBooking.get().getDestination());

        // Verify interaction with the mock
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteBooking() {
        // Arrange
        doNothing().when(bookingRepository).delete(any(Booking.class)); // Mock delete method

        // Act
        bookingRepository.delete(booking);

        // Assert
        verify(bookingRepository, times(1)).delete(any(Booking.class)); // Ensure delete was called
    }

    @Test
    public void testUpdateBooking() {
        // Arrange
        booking.setDestination("Delhi"); // Change the destination
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking); // Mock save method for update

        // Act
        Booking updatedBooking = bookingRepository.save(booking);

        // Assert
        assertEquals("Delhi", updatedBooking.getDestination());

        // Verify interaction with the mock
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testFindBookingByUser() {
        // This is an example to show how we can test custom methods, assuming there's a method like findByUser in the repository
        when(bookingRepository.findByUser(user)).thenReturn(Arrays.asList(booking)); // Mock method findByUser

        // Act
        List<Booking> bookings = bookingRepository.findByUser(user);

        // Assert
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        assertEquals("Varanasi", bookings.get(0).getDestination());

        // Verify interaction with the mock
        verify(bookingRepository, times(1)).findByUser(user);
    }
}
