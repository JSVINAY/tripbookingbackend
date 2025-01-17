package com.training.tripbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.TripPackage;

@Repository
public interface TripPackageRepository extends JpaRepository<TripPackage, Long> {

}