package com.training.tripbooking.repositories;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.training.tripbooking.model.Payment;
import com.training.tripbooking.repositories.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentRepositoryTest {

    @Mock
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Payment object
        payment = new Payment();
        payment.setId(1L);
        payment.setCardNumber("4111111111111111");
        payment.setExpiryDate("12/25");
        payment.setCvv("123");
        payment.setPaymentMethod("Credit Card");
        payment.setPaymentSuccessful(true);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        // Act
        Optional<Payment> foundPayment = paymentRepository.findById(1L);

        // Assert
        assertTrue(foundPayment.isPresent());
        assertEquals("4111111111111111", foundPayment.get().getCardNumber());
        assertEquals("Credit Card", foundPayment.get().getPaymentMethod());

        // Verify interaction
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    public void testSavePayment() {
        // Arrange
        when(paymentRepository.save(payment)).thenReturn(payment);

        // Act
        Payment savedPayment = paymentRepository.save(payment);

        // Assert
        assertNotNull(savedPayment);
        assertEquals("4111111111111111", savedPayment.getCardNumber());
        assertTrue(savedPayment.isPaymentSuccessful());

        // Verify interaction
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        doNothing().when(paymentRepository).deleteById(1L);

        // Act
        paymentRepository.deleteById(1L);

        // Assert
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        // Arrange
        when(paymentRepository.findAll()).thenReturn(List.of(payment));

        // Act
        Iterable<Payment> payments = paymentRepository.findAll();

        // Assert
        assertNotNull(payments);
        assertTrue(payments.iterator().hasNext());
        assertEquals("4111111111111111", payments.iterator().next().getCardNumber());

        // Verify interaction
        verify(paymentRepository, times(1)).findAll();
    }
}
