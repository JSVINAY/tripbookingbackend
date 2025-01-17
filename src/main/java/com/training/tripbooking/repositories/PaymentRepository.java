package com.training.tripbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}