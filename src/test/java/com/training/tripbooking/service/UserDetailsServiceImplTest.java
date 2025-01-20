package com.training.tripbooking.service;


import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        // Initialize the user entity before each test
        userEntity = new UserEntity("testuser", "testuser@example.com", "password", "123 Street", "1234567890",
                "Male", "California", "USA", "90001");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        userEntity.setRoles(new HashSet<>());  // Set roles to an empty set (or null if not initialized)
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);  // Ensure userDetails is not null
        assertEquals("testuser", userDetails.getUsername());  // Ensure the username matches
        assertTrue(userDetails.getAuthorities().isEmpty());  // Ensure authorities (roles) is empty
        verify(userRepository, times(1)).findByUsername("testuser");  // Ensure findByUsername was called once
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("testuser");
        });  // Ensure that UsernameNotFoundException is thrown
        verify(userRepository, times(1)).findByUsername("testuser");  // Ensure findByUsername was called once
    }

    @Test
    void testLoadUserByUsername_UsernameNull() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);  // Test for null username
        });
    }

    @Test
    void testLoadUserByUsername_EmptyUsername() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("");  // Test for empty username
        });
    }
}