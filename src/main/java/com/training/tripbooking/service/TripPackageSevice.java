package com.training.tripbooking.service;

import java.util.List;

import com.training.tripbooking.model.TripPackage;

public interface TripPackageSevice {
	List<TripPackage> getAllPackages();

    TripPackage getPackageById(Long id);

    TripPackage createPackage(TripPackage tripPackage);

    TripPackage updatePackage(Long id, TripPackage tripPackage);

    void deletePackage(Long id);
}
