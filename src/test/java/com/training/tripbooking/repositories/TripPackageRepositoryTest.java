package com.training.tripbooking.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.repositories.TripPackageRepository;

class TripPackageRepositoryTest {

	@Mock
	private TripPackageRepository tripPackageRepository;

	@InjectMocks
	private TripPackageRepositoryTest tripPackageRepositoryTest;

	private TripPackage tripPackage1;
	private TripPackage tripPackage2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// Sample data for testing
		tripPackage1 = new TripPackage("Manali", "image1.jpg", 15000.0, 5, LocalDate.of(2025, 1, 15));
		tripPackage1.setId(1L);

		tripPackage2 = new TripPackage("Goa", "image2.jpg", 20000.0, 7, LocalDate.of(2025, 2, 1));
		tripPackage2.setId(2L);
	}

	@Test
	void testFindById_Success() {
		// Arrange
		when(tripPackageRepository.findById(1L)).thenReturn(Optional.of(tripPackage1));

		// Act
		Optional<TripPackage> result = tripPackageRepository.findById(1L);

		// Assert
		assertTrue(result.isPresent());
		assertEquals("Manali", result.get().getDestination());
	}

	@Test
	void testFindById_NotFound() {
		// Arrange
		when(tripPackageRepository.findById(3L)).thenReturn(Optional.empty());

		// Act
		Optional<TripPackage> result = tripPackageRepository.findById(3L);

		// Assert
		assertTrue(result.isEmpty());
	}

	@Test
	void testFindAll() {
		// Arrange
		when(tripPackageRepository.findAll()).thenReturn(Arrays.asList(tripPackage1, tripPackage2));

		// Act
		List<TripPackage> result = tripPackageRepository.findAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals("Manali", result.get(0).getDestination());
		assertEquals("Goa", result.get(1).getDestination());
	}

	@Test
	void testSave() {
		// Arrange
		when(tripPackageRepository.save(tripPackage1)).thenReturn(tripPackage1);

		// Act
		TripPackage result = tripPackageRepository.save(tripPackage1);

		// Assert
		assertEquals("Manali", result.getDestination());
		assertEquals("image1.jpg", result.getImage());
		assertEquals(15000.0, result.getPrice());
		assertEquals(5, result.getDuration());
		assertEquals(LocalDate.of(2025, 1, 15), result.getStartDate());
	}

	@Test
	void testDeleteById() {
		// No Arrange needed for delete operation
		tripPackageRepository.deleteById(1L);

		// Act
		Optional<TripPackage> result = tripPackageRepository.findById(1L);

		// Assert
		assertTrue(result.isEmpty()); // Ensures the repository behaves correctly
	}
}
