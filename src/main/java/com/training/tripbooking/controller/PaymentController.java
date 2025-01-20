package com.training.tripbooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.tripbooking.model.Payment;
import com.training.tripbooking.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class); // Initialize logger

    @Autowired
    private PaymentService paymentService;

    // Handle payment processing
    @PostMapping("/makepayment")
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
        logger.info("Received Payment request: {}", payment);

        // Check if any field is missing
        if (payment.getCardNumber() == null || payment.getExpiryDate() == null || payment.getCvv() == null || payment.getPaymentMethod() == null) {
            logger.warn("Missing fields in payment request: {}", payment);
            return ResponseEntity.badRequest().body("Please fill in all fields.");
        }

        try {
            // Process payment and save
            Payment processedPayment = paymentService.processPayment(payment);
            logger.info("Processed Payment: {}", processedPayment);

            if (processedPayment.isPaymentSuccessful()) {
                logger.info("Payment processed successfully for payment ID: {}", processedPayment.getId());
                return ResponseEntity.ok("Payment processed successfully.");
            } else {
                logger.error("Payment failed for payment ID: {}", processedPayment.getId());
                return ResponseEntity.status(500).body("Payment failed. Please try again.");
            }
        } catch (Exception e) {
            // Log any error
            logger.error("Error processing payment: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error processing payment: " + e.getMessage());
        }
    }
}
