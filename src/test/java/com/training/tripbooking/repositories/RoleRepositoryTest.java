package com.training.tripbooking.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.tripbooking.model.ERole;
import com.training.tripbooking.model.Role;
import com.training.tripbooking.repositories.RoleRepository;

class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleRepositoryTest roleRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName_Success() {
        // Arrange
        ERole roleName = ERole.ROLE_USER;
        Role role = new Role(1, roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleRepository.findByName(roleName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getName());
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        ERole roleName = ERole.ROLE_ADMIN;
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        // Act
        Optional<Role> result = roleRepository.findByName(roleName);

        // Assert
        assertTrue(result.isEmpty());
    }
}
