package com.training.tripbooking.controller;

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

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makepayment")
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
        // Log received payment
        System.out.println("Received Payment: " + payment);

        // Check if any field is missing
        if (payment.getCardNumber() == null || payment.getExpiryDate() == null || payment.getCvv() == null || payment.getPaymentMethod() == null) {
            return ResponseEntity.badRequest().body("Please fill in all fields.");
        }

        try {
            // Process payment and save
            Payment processedPayment = paymentService.processPayment(payment);

            // Log result
            System.out.println("Processed Payment: " + processedPayment);

            if (processedPayment.isPaymentSuccessful()) {
                return ResponseEntity.ok("Payment processed successfully.");
            } else {
                return ResponseEntity.status(500).body("Payment failed. Please try again.");
            }
        } catch (Exception e) {
            // Log any error
            System.out.println("Error processing payment: " + e.getMessage());
            return ResponseEntity.status(500).body("Error processing payment: " + e.getMessage());
        }
    }
}
