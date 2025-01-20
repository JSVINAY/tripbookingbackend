package com.training.tripbooking.service;

import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.repositories.TripPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripPackageServiceImplTest {

    @Mock
    private TripPackageRepository tripPackageRepository;

    @InjectMocks
    private TripPackageServiceImpl tripPackageService;

    private TripPackage tripPackage;

    @BeforeEach
    void setUp() {
        // Initialize a new TripPackage object before each test
        tripPackage = new TripPackage("Paris", "paris.jpg", 500.0, 7, LocalDate.of(2025, 5, 15));
    }

    @Test
    void testGetAllPackages() {
        // Arrange
        List<TripPackage> tripPackages = List.of(tripPackage);
        when(tripPackageRepository.findAll()).thenReturn(tripPackages);

        // Act
        List<TripPackage> result = tripPackageService.getAllPackages();

        // Assert
        assertNotNull(result);  // Ensure the result is not null
        assertEquals(1, result.size());  // Ensure there is one package
        assertEquals("Paris", result.get(0).getDestination());  // Ensure the destination is correct
        verify(tripPackageRepository, times(1)).findAll();  // Ensure the findAll method was called
    }

    @Test
    void testGetPackageByIdFound() {
        // Arrange
        when(tripPackageRepository.findById(1L)).thenReturn(Optional.of(tripPackage));

        // Act
        TripPackage result = tripPackageService.getPackageById(1L);

        // Assert
        assertNotNull(result);  // Ensure the result is not null
        assertEquals("Paris", result.getDestination());  // Ensure the destination matches
        verify(tripPackageRepository, times(1)).findById(1L);  // Ensure findById was called
    }

    @Test
    void testGetPackageByIdNotFound() {
        // Arrange
        when(tripPackageRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        TripPackage result = tripPackageService.getPackageById(1L);

        // Assert
        assertNull(result);  // Ensure result is null when package is not found
        verify(tripPackageRepository, times(1)).findById(1L);  // Ensure findById was called
    }

    @Test
    void testCreatePackage() {
        // Arrange
        when(tripPackageRepository.save(tripPackage)).thenReturn(tripPackage);

        // Act
        TripPackage savedPackage = tripPackageService.createPackage(tripPackage);

        // Assert
        assertNotNull(savedPackage);  // Ensure the saved package is not null
        assertEquals("Paris", savedPackage.getDestination());  // Ensure the destination matches
        assertEquals(500.0, savedPackage.getPrice());  // Ensure the price matches
        verify(tripPackageRepository, times(1)).save(tripPackage);  // Ensure save method is called
    }

    @Test
    void testUpdatePackageFound() {
        // Arrange
        tripPackage.setPrice(600.0);  // Update the price for the test
        when(tripPackageRepository.existsById(1L)).thenReturn(true);
        when(tripPackageRepository.save(tripPackage)).thenReturn(tripPackage);

        // Act
        TripPackage updatedPackage = tripPackageService.updatePackage(1L, tripPackage);

        // Assert
        assertNotNull(updatedPackage);  // Ensure the updated package is not null
        assertEquals(600.0, updatedPackage.getPrice());  // Ensure the price is updated
        verify(tripPackageRepository, times(1)).save(tripPackage);  // Ensure save method was called
    }

    @Test
    void testUpdatePackageNotFound() {
        // Arrange
        when(tripPackageRepository.existsById(1L)).thenReturn(false);

        // Act
        TripPackage result = tripPackageService.updatePackage(1L, tripPackage);

        // Assert
        assertNull(result);  // Ensure result is null if package does not exist
        verify(tripPackageRepository, times(1)).existsById(1L);  // Ensure existsById was called
    }

    @Test
    void testDeletePackage() {
        // Arrange
        when(tripPackageRepository.existsById(1L)).thenReturn(true);

        // Act
        tripPackageService.deletePackage(1L);

        // Assert
        verify(tripPackageRepository, times(1)).deleteById(1L);  // Ensure deleteById was called
    }

    @Test
    void testDeletePackageNotFound() {
        // Arrange
        when(tripPackageRepository.existsById(1L)).thenReturn(false);

        // Act
        tripPackageService.deletePackage(1L);

        // Assert
        verify(tripPackageRepository, times(0)).deleteById(1L);  // Ensure deleteById was not called
    }
}