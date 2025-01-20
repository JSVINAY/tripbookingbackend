package com.training.tripbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.service.TripPackageSevice;

import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin()
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class); // Initialize logger

    @Autowired
    private TripPackageSevice tripPackageService;

    // Get all trip packages
    @GetMapping("/packages")
    public List<TripPackage> getAllPackages() {
        logger.info("Getting all trip packages");
        List<TripPackage> packages = tripPackageService.getAllPackages();
        logger.info("Retrieved {} trip packages", packages.size());
        return packages;
    }

    // Get a specific trip package by ID
    @GetMapping("/packages/{id}")
    public ResponseEntity<TripPackage> getPackageById(@PathVariable Long id) {
        logger.info("Fetching trip package with ID: {}", id);
        TripPackage tripPackage = tripPackageService.getPackageById(id);
        if (tripPackage != null) {
            logger.info("Found trip package: {}", tripPackage);
            return ResponseEntity.ok(tripPackage);
        } else {
            logger.warn("Trip package with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new trip package
    @PostMapping("/packages")
    public ResponseEntity<TripPackage> createPackage(@RequestBody TripPackage tripPackage) {
        logger.info("Received request to create a new trip package: {}", tripPackage);
        if (tripPackage == null) {
            logger.error("Received null trip package");
            return ResponseEntity.badRequest().body(null); // Handle null input
        }
        TripPackage createdPackage = tripPackageService.createPackage(tripPackage);
        logger.info("Successfully created trip package with ID: {}", createdPackage.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }

    // Update an existing trip package
    @PutMapping("/packages/{id}")
    public ResponseEntity<TripPackage> updatePackage(@PathVariable Long id, @RequestBody TripPackage tripPackage) {
        logger.info("Received request to update trip package with ID: {}", id);
        if (tripPackage == null) {
            logger.error("Received null trip package for update");
            return ResponseEntity.badRequest().body(null); // Handle null input
        }
        TripPackage updatedPackage = tripPackageService.updatePackage(id, tripPackage);
        if (updatedPackage != null) {
            logger.info("Successfully updated trip package with ID: {}", updatedPackage.getId());
            return ResponseEntity.ok(updatedPackage);
        } else {
            logger.warn("Trip package with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a trip package by ID
    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        logger.info("Received request to delete trip package with ID: {}", id);
        tripPackageService.deletePackage(id);
        logger.info("Successfully deleted trip package with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
