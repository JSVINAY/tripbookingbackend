package com.training.tripbooking.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.repositories.TripPackageRepository;

@Service
public class TripPackageServiceImpl implements TripPackageSevice {

    private static final Logger logger = LoggerFactory.getLogger(TripPackageServiceImpl.class); // Initialize logger

    @Autowired
    private TripPackageRepository tripPackageRepository;

    @Override
    public List<TripPackage> getAllPackages() {
        logger.info("Fetching all trip packages");

        List<TripPackage> tripPackages = tripPackageRepository.findAll();
        logger.info("Retrieved {} trip packages", tripPackages.size());

        return tripPackages;
    }

    @Override
    public TripPackage getPackageById(Long id) {
        logger.info("Fetching trip package with ID: {}", id);

        Optional<TripPackage> tripPackage = tripPackageRepository.findById(id);
        if (tripPackage.isPresent()) {
            logger.info("Found trip package with ID: {}", id);
            return tripPackage.get();
        } else {
            logger.warn("Trip package with ID {} not found", id);
            return null; // You can throw an exception or return a custom response based on your design.
        }
    }

    @Override
    public TripPackage createPackage(TripPackage tripPackage) {
        logger.info("Creating a new trip package: {}", tripPackage);

        TripPackage savedPackage = tripPackageRepository.save(tripPackage);
        logger.info("Trip package created with ID: {}", savedPackage.getId());

        return savedPackage;
    }

    @Override
    public TripPackage updatePackage(Long id, TripPackage tripPackage) {
        logger.info("Updating trip package with ID: {}", id);

        if (tripPackageRepository.existsById(id)) {
            tripPackage.setId(id); // Ensure the correct package ID is set
            TripPackage updatedPackage = tripPackageRepository.save(tripPackage);
            logger.info("Trip package updated with ID: {}", id);
            return updatedPackage;
        } else {
            logger.warn("Trip package with ID {} not found for update", id);
            return null; // Or throw an exception if package doesn't exist
        }
    }

    @Override
    public void deletePackage(Long id) {
        logger.info("Deleting trip package with ID: {}", id);

        if (tripPackageRepository.existsById(id)) {
            tripPackageRepository.deleteById(id);
            logger.info("Successfully deleted trip package with ID: {}", id);
        } else {
            logger.warn("Trip package with ID {} not found for deletion", id);
        }
    }
}
