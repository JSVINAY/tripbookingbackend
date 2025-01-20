package com.training.tripbooking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.tripbooking.controller.PaymentController;
import com.training.tripbooking.model.Payment;
import com.training.tripbooking.service.PaymentService;

@ExtendWith(MockitoExtension.class) // Ensure Mockito extension is enabled
class PaymentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PaymentService paymentService;

	@InjectMocks
	private PaymentController paymentController;

	private Payment payment;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

		// Sample payment object
		payment = new Payment();
		payment.setCardNumber("1234567812345678");
		payment.setExpiryDate("12/23");
		payment.setCvv("123");
		payment.setPaymentMethod("Credit Card");
	}

	@Test
	void testProcessPaymentSuccess() throws Exception {
		when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);
		payment.setPaymentSuccessful(true);

		mockMvc.perform(
				post("/api/payment/makepayment").contentType(MediaType.APPLICATION_JSON).content(asJsonString(payment)))
				.andExpect(status().isOk()).andExpect(content().string("Payment processed successfully."));
	}

	@Test
	void testProcessPaymentFailure() throws Exception {
		when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);
		payment.setPaymentSuccessful(false);

		mockMvc.perform(
				post("/api/payment/makepayment").contentType(MediaType.APPLICATION_JSON).content(asJsonString(payment)))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Payment failed. Please try again."));
	}

	@Test
	void testProcessPaymentMissingFields() throws Exception {
		Payment invalidPayment = new Payment();
		invalidPayment.setCardNumber("1234567812345678");

		mockMvc.perform(post("/api/payment/makepayment").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(invalidPayment))).andExpect(status().isBadRequest())
				.andExpect(content().string("Please fill in all fields."));
	}

	@Test
	void testProcessPaymentException() throws Exception {
		when(paymentService.processPayment(any(Payment.class)))
				.thenThrow(new RuntimeException("Payment gateway error"));

		mockMvc.perform(
				post("/api/payment/makepayment").contentType(MediaType.APPLICATION_JSON).content(asJsonString(payment)))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Error processing payment: Payment gateway error"));
	}

	private String asJsonString(final Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
