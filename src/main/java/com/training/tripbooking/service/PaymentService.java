package com.training.tripbooking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.Payment;
import com.training.tripbooking.repositories.PaymentRepository;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class); // Initialize logger

    @Autowired
    private PaymentRepository paymentRepository;

    // Process the payment and save it to the database
    public Payment processPayment(Payment payment) {
        logger.info("Processing payment: {}", payment);

        // Simulate payment processing (mark as successful for testing)
        payment.setPaymentSuccessful(true);

        // Log payment status
        logger.info("Payment processed successfully. Payment ID: {}", payment.getId());

        // Save payment details in the database
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment saved in the database with ID: {}", savedPayment.getId());

        return savedPayment;
    }
}
