package com.training.tripbooking.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.repositories.UserRepository;

class UserRepositoryTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserRepositoryTest userRepositoryTest;

	private UserEntity user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// Sample user data
		user = new UserEntity("testuser", "testuser@example.com", "password123", "123 Street", "1234567890", "Male",
				"California", "USA", "12345");
		user.setId(1L);
	}

	@Test
	void testFindByUsername_Success() {
		// Arrange
		when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

		// Act
		Optional<UserEntity> result = userRepository.findByUsername("testuser");

		// Assert
		assertTrue(result.isPresent());
		assertNotNull(result.get());
		assertTrue(result.get().getUsername().equals("testuser"));
	}

	@Test
	void testFindByUsername_NotFound() {
		// Arrange
		when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

		// Act
		Optional<UserEntity> result = userRepository.findByUsername("nonexistentuser");

		// Assert
		assertFalse(result.isPresent());
	}

	@Test
	void testExistsByUsername_True() {
		// Arrange
		when(userRepository.existsByUsername("testuser")).thenReturn(true);

		// Act
		boolean exists = userRepository.existsByUsername("testuser");

		// Assert
		assertTrue(exists);
	}

	@Test
	void testExistsByUsername_False() {
		// Arrange
		when(userRepository.existsByUsername("nonexistentuser")).thenReturn(false);

		// Act
		boolean exists = userRepository.existsByUsername("nonexistentuser");

		// Assert
		assertFalse(exists);
	}

	@Test
	void testExistsByEmail_True() {
		// Arrange
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

		// Act
		boolean exists = userRepository.existsByEmail("testuser@example.com");

		// Assert
		assertTrue(exists);
	}

	@Test
	void testExistsByEmail_False() {
		// Arrange
		when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

		// Act
		boolean exists = userRepository.existsByEmail("nonexistent@example.com");

		// Assert
		assertFalse(exists);
	}
}
