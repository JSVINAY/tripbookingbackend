package com.training.tripbooking.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.ERole;
import com.training.tripbooking.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}