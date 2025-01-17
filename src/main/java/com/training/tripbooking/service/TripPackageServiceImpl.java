package com.training.tripbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.repositories.TripPackageRepository;

@Service
public class TripPackageServiceImpl implements TripPackageSevice{

	 @Autowired
	    private TripPackageRepository tripPackageRepository;
	 
	@Override
	public List<TripPackage> getAllPackages() {
		  return tripPackageRepository.findAll();
	}

	@Override
	public TripPackage getPackageById(Long id) {
		Optional<TripPackage> tripPackage = tripPackageRepository.findById(id);
        return tripPackage.orElse(null); //
	}

	@Override
	public TripPackage createPackage(TripPackage tripPackage) {
		// TODO Auto-generated method stub
		 return tripPackageRepository.save(tripPackage);
	}

	@Override
	public TripPackage updatePackage(Long id, TripPackage tripPackage) {
		if (tripPackageRepository.existsById(id)) {
            tripPackage.setId(id); // Ensure the correct package ID is set
            return tripPackageRepository.save(tripPackage);
        } else {
            return null; // Or throw an exception if package doesn't exist
        }
	}

	@Override
	public void deletePackage(Long id) {
		if (tripPackageRepository.existsById(id)) {
            tripPackageRepository.deleteById(id);
        }
	}
}
