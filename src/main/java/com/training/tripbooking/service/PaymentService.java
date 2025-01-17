package com.training.tripbooking.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.Payment;
import com.training.tripbooking.repositories.PaymentRepository;
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        // Simulate payment processing (set to true for testing)
        System.out.println("Processing payment: " + payment);

        // Always mark the payment as successful for testing
        payment.setPaymentSuccessful(true);

        // Save payment details in the database (replace with actual logic if necessary)
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment;
    }
}