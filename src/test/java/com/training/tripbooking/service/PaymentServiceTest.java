package com.training.tripbooking.service;

import com.training.tripbooking.model.Payment;
import com.training.tripbooking.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        // Set up a new Payment object before each test
        payment = new Payment();
        payment.setCardNumber("1234567812345678");
        payment.setExpiryDate("12/25");
        payment.setCvv("123");
        payment.setPaymentMethod("Credit Card");
        payment.setPaymentSuccessful(false); // Initially, payment is not successful
    }

    @Test
    void testProcessPayment() {
        // Arrange
        when(paymentRepository.save(payment)).thenReturn(payment);  // Mock save method

        // Act
        Payment processedPayment = paymentService.processPayment(payment);

        // Assert
        assertNotNull(processedPayment);  // Ensure the processed payment is not null
        assertTrue(processedPayment.isPaymentSuccessful());  // Ensure the payment is marked as successful
        assertEquals("1234567812345678", processedPayment.getCardNumber());  // Ensure the card number matches
        assertEquals("Credit Card", processedPayment.getPaymentMethod());  // Ensure the payment method matches
        verify(paymentRepository, times(1)).save(payment);  // Ensure save() is called once
    }
}