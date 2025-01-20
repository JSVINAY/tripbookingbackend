package com.training.tripbooking.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.tripbooking.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
	

}
