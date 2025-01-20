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
@CrossOrigin()
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private TripPackageSevice tripPackageService;

    // Get all trip packages
    @GetMapping("/packages")
    public List<TripPackage> getAllPackages() {
        return tripPackageService.getAllPackages();
    }

    // Get a specific trip package by ID
    @GetMapping("/packages/{id}")
    public ResponseEntity<TripPackage> getPackageById(@PathVariable Long id) {
        TripPackage tripPackage = tripPackageService.getPackageById(id);
        if (tripPackage != null) {
            return ResponseEntity.ok(tripPackage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new trip package
    @PostMapping("/packages")
    public ResponseEntity<TripPackage> createPackage(@RequestBody TripPackage tripPackage) {
        System.out.println("Received Trip Package: " + tripPackage);  // Log received package data
        if (tripPackage == null) {
            return ResponseEntity.badRequest().body(null); // Handle null input
        }
        TripPackage createdPackage = tripPackageService.createPackage(tripPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }




    // Update an existing trip package
    @PutMapping("/packages/{id}")
    public ResponseEntity<TripPackage> updatePackage(@PathVariable Long id, @RequestBody TripPackage tripPackage) {
        if (tripPackage == null) {
            return ResponseEntity.badRequest().body(null); // Handle null input
        }
        TripPackage updatedPackage = tripPackageService.updatePackage(id, tripPackage);
        if (updatedPackage != null) {
            return ResponseEntity.ok(updatedPackage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Delete a trip package by ID
    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        tripPackageService.deletePackage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}