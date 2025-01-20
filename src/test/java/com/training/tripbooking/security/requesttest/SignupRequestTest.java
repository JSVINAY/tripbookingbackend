package com.training.tripbooking.security.requesttest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.training.tripbooking.security.request.SignupRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class SignupRequestTest {

	private final Validator validator;

	// Initialize the Validator
	public SignupRequestTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	@Test
	void testValidSignupRequest() {
		SignupRequest signupRequest = new SignupRequest("validUser", "ValidPassword123", Set.of("user"),
				"user@example.com", "1234567890", "male", "123 Main St", "California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert no violations
		assertTrue(violations.isEmpty(), "There should be no validation errors");
	}

	@Test
	void testBlankUsername() {
		SignupRequest signupRequest = new SignupRequest("", "ValidPassword123", Set.of("user"), "user@example.com",
				"1234567890", "male", "123 Main St", "California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert that violations contain a message for blank username
		assertFalse(violations.isEmpty(), "Username should not be blank");
		for (ConstraintViolation<SignupRequest> violation : violations) {
			if (violation.getPropertyPath().toString().equals("username")) {
				assertEquals("must not be blank", violation.getMessage());
			}
		}
	}

	@Test
	void testBlankPassword() {
		SignupRequest signupRequest = new SignupRequest("validUser", "", Set.of("user"), "user@example.com",
				"1234567890", "male", "123 Main St", "California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert that violations contain a message for blank password
		assertFalse(violations.isEmpty(), "Password should not be blank");
		for (ConstraintViolation<SignupRequest> violation : violations) {
			if (violation.getPropertyPath().toString().equals("password")) {
				assertEquals("must not be blank", violation.getMessage());
			}
		}
	}

	@Test
	void testInvalidEmail() {
		SignupRequest signupRequest = new SignupRequest("validUser", "ValidPassword123", Set.of("user"),
				"invalid-email", "1234567890", "male", "123 Main St", "California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert that violations contain a message for invalid email
		assertFalse(violations.isEmpty(), "Email should be valid");
		for (ConstraintViolation<SignupRequest> violation : violations) {
			if (violation.getPropertyPath().toString().equals("email")) {
				assertEquals("must be a well-formed email address", violation.getMessage());
			}
		}
	}

	@Test
	void testInvalidPostalCode() {
		SignupRequest signupRequest = new SignupRequest("validUser", "ValidPassword123", Set.of("user"),
				"user@example.com", "1234567890", "male", "123 Main St", "California", "USA", "123");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert that violations contain a message for invalid postal code
		assertFalse(violations.isEmpty(), "Postal Code should match the specified pattern");
		for (ConstraintViolation<SignupRequest> violation : violations) {
			if (violation.getPropertyPath().toString().equals("postalcode")) {
				assertEquals("Invalid postal code format", violation.getMessage());
			}
		}
	}

	@Test
	void testValidPostalCode() {
		SignupRequest signupRequest = new SignupRequest("validUser", "ValidPassword123", Set.of("user"),
				"user@example.com", "1234567890", "male", "123 Main St", "California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert no violations for valid postal code
		assertTrue(violations.isEmpty(), "There should be no validation errors for a valid postal code");
	}

	@Test
	void testTooLongUsername() {
		SignupRequest signupRequest = new SignupRequest("thisUsernameIsWayTooLongToBeValid", // 35 characters
				"ValidPassword123", Set.of("user"), "user@example.com", "1234567890", "male", "123 Main St",
				"California", "USA", "12345");

		// Validate the SignupRequest
		Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

		// Assert that violations contain a message for too long username
		assertFalse(violations.isEmpty(), "Username should be less than or equal to 20 characters");
		for (ConstraintViolation<SignupRequest> violation : violations) {
			if (violation.getPropertyPath().toString().equals("username")) {
				assertEquals("size must be between 0 and 20", violation.getMessage());
			}
		}
	}

	
}
