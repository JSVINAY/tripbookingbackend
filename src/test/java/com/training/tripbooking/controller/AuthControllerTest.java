package com.training.tripbooking.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.tripbooking.model.ERole;
import com.training.tripbooking.model.Role;
import com.training.tripbooking.repositories.RoleRepository;
import com.training.tripbooking.repositories.UserRepository;
import com.training.tripbooking.security.jwt.JwtUtils;
import com.training.tripbooking.security.request.LoginRequest;
import com.training.tripbooking.security.request.SignupRequest;
import com.training.tripbooking.service.UserDetailsImpl;

class AuthControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PasswordEncoder encoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtils jwtUtils;

	@InjectMocks
	private AuthController authController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	@Test
	void testAuthenticateUser() throws Exception {
	    // Arrange
	    String username = "testuser";
	    String password = "testpassword";
	    String jwtToken = "mockedJwtToken";

	    LoginRequest loginRequest = new LoginRequest(username, password);

	    // Mock AuthenticationManager
	    Authentication mockAuthentication = Mockito.mock(Authentication.class);
	    when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
	            .thenReturn(mockAuthentication);

	    // Mock JwtUtils
	    when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn(jwtToken);

	    // Mock UserDetails
	    UserDetailsImpl mockUserDetails = new UserDetailsImpl(1L, username, "test@example.com", password, List.of());
	    when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);

	    // Act & Assert
	    mockMvc.perform(post("/api/auth/signin")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(loginRequest)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.token").value(jwtToken)) // Ensure this matches the actual response field
	            .andExpect(jsonPath("$.username").value(username))
	            .andExpect(jsonPath("$.email").value("test@example.com"));
	}


	@Test
	void testRegisterUserWithAdditionalFields() throws Exception {
		// Arrange
		SignupRequest signupRequest = new SignupRequest("testuser", "password", new HashSet<>(Arrays.asList("user")),
				"testuser@example.com", "1234567890", "Male", "123 Test Street", "Test State", "Test Country",
				"123456");

		// Mock repository responses
		when(userRepository.existsByUsername("testuser")).thenReturn(false);
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);

		// Mock role retrieval
		Role userRole = new Role(ERole.ROLE_USER);
		when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));

		// Act & Assert
		mockMvc.perform(
				post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(signupRequest)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("User registered successfully!"));

		// Verify that the user repository save method was called
		verify(userRepository, times(1)).save(Mockito.argThat(user -> user.getUsername().equals("testuser")
				&& user.getEmail().equals("testuser@example.com") && user.getPhonenumber().equals("1234567890")
				&& user.getGender().equals("Male") && user.getAddress().equals("123 Test Street")
				&& user.getState().equals("Test State") && user.getCountry().equals("Test Country")
				&& user.getPostalcode().equals("123456") && user.getRoles().contains(userRole)));
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
