package com.training.tripbooking.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.training.tripbooking.model.Passenger;
import com.training.tripbooking.repositories.PassengerRepository;

@ExtendWith(MockitoExtension.class)
public class PassengerRepositoryTest {

    @Mock
    private PassengerRepository passengerRepository;

    private Passenger passenger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Passenger object
        passenger = new Passenger(1L, "John Doe", 30, "Male", "Veg", "ID12345", "Passport");
    }

    @Test
    public void testFindByGovId() {
        // Arrange
        when(passengerRepository.findByGovId("ID12345")).thenReturn(passenger);

        // Act
        Passenger foundPassenger = passengerRepository.findByGovId("ID12345");

        // Assert
        assertNotNull(foundPassenger);
        assertEquals("John Doe", foundPassenger.getName());
        assertEquals("ID12345", foundPassenger.getGovId());

        // Verify interaction
        verify(passengerRepository, times(1)).findByGovId("ID12345");
    }

    @Test
    public void testSavePassenger() {
        // Arrange
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        // Act
        Passenger savedPassenger = passengerRepository.save(passenger);

        // Assert
        assertNotNull(savedPassenger);
        assertEquals("John Doe", savedPassenger.getName());
        assertEquals("ID12345", savedPassenger.getGovId());

        // Verify interaction
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(passengerRepository.findById(1)).thenReturn(Optional.of(passenger));

        // Act
        Optional<Passenger> foundPassenger = passengerRepository.findById(1);

        // Assert
        assertTrue(foundPassenger.isPresent());
        assertEquals("John Doe", foundPassenger.get().getName());

        // Verify interaction
        verify(passengerRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        doNothing().when(passengerRepository).deleteById(1);

        // Act
        passengerRepository.deleteById(1);

        // Assert
        verify(passengerRepository, times(1)).deleteById(1);
    }

    @Test
    public void testFindAll() {
        // Arrange
        when(passengerRepository.findAll()).thenReturn(List.of(passenger));

        // Act
        Iterable<Passenger> passengers = passengerRepository.findAll();

        // Assert
        assertNotNull(passengers);
        assertTrue(passengers.iterator().hasNext());
        assertEquals("John Doe", passengers.iterator().next().getName());

        // Verify interaction
        verify(passengerRepository, times(1)).findAll();
    }
}

