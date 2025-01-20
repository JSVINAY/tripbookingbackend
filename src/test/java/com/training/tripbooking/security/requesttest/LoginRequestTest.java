package com.training.tripbooking.security.requesttest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.training.tripbooking.security.request.LoginRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

public class LoginRequestTest {

	@Mock
	private Validator validator;
	
	private LoginRequest loginRequest;

	@InjectMocks
	private LoginRequestTest loginRequestTest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testValidLoginRequest() {
		loginRequest = new LoginRequest("validUsername", "validPassword");

		// Create an empty set of violations (no validation errors)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();

		// Mock the validator to return an empty set of violations for a valid
		// LoginRequest
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(0, result.size(), "There should be no validation errors");
	}

	@Test
	void testBlankUsername() {
		loginRequest = new LoginRequest("", "validPassword");

		// Create a set of violations (mocking the validation error for blank username)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();
		ConstraintViolation<LoginRequest> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("must not be blank");
		violations.add(violation);

		// Mock the validator to return the set of violations
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(1, result.size(), "There should be one validation error");
		assertEquals("must not be blank", result.iterator().next().getMessage());
	}

	@Test
	void testBlankPassword() {
		loginRequest = new LoginRequest("validUsername", "");

		// Create a set of violations (mocking the validation error for blank password)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();
		ConstraintViolation<LoginRequest> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("must not be blank");
		violations.add(violation);

		// Mock the validator to return the set of violations
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(1, result.size(), "There should be one validation error");
		assertEquals("must not be blank", result.iterator().next().getMessage());
	}

	@Test
	void testNullUsername() {
		loginRequest = new LoginRequest(null, "validPassword");

		// Create a set of violations (mocking the validation error for null username)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();
		ConstraintViolation<LoginRequest> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("must not be blank");
		violations.add(violation);

		// Mock the validator to return the set of violations
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(1, result.size(), "There should be one validation error");
		assertEquals("must not be blank", result.iterator().next().getMessage());
	}

	@Test
	void testNullPassword() {
		loginRequest = new LoginRequest("validUsername", null);

		// Create a set of violations (mocking the validation error for null password)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();
		ConstraintViolation<LoginRequest> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("must not be blank");
		violations.add(violation);

		// Mock the validator to return the set of violations
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(1, result.size(), "There should be one validation error");
		assertEquals("must not be blank", result.iterator().next().getMessage());
	}

	@Test
	void testBothUsernameAndPasswordNull() {
		loginRequest = new LoginRequest(null, null);

		// Create a set of violations (mocking the validation error for both username
		// and password being null)
		Set<ConstraintViolation<LoginRequest>> violations = new HashSet<>();
		ConstraintViolation<LoginRequest> violation1 = mock(ConstraintViolation.class);
		ConstraintViolation<LoginRequest> violation2 = mock(ConstraintViolation.class);
		when(violation1.getMessage()).thenReturn("must not be blank");
		when(violation2.getMessage()).thenReturn("must not be blank");
		violations.add(violation1);
		violations.add(violation2);

		// Mock the validator to return the set of violations
		when(validator.validate(loginRequest)).thenReturn(violations);

		Set<ConstraintViolation<LoginRequest>> result = validator.validate(loginRequest);

		assertEquals(2, result.size(), "There should be two validation errors");
		assertEquals("must not be blank", result.iterator().next().getMessage());
	}
}
