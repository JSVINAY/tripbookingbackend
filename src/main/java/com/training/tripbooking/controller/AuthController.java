package com.training.tripbooking.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.tripbooking.model.ERole;
import com.training.tripbooking.model.Role;
import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.repositories.RoleRepository;
import com.training.tripbooking.repositories.UserRepository;
import com.training.tripbooking.security.jwt.JwtUtils;
import com.training.tripbooking.security.request.LoginRequest;
import com.training.tripbooking.security.request.SignupRequest;
import com.training.tripbooking.security.response.JwtResponse;
import com.training.tripbooking.security.response.MessageResponse;
import com.training.tripbooking.service.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class); // Initialize logger

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    // Sign-in (Authenticate user)
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Attempting to authenticate user with username: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        logger.info("User {} successfully authenticated", loginRequest.getUsername());
        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    // Sign-up (Register new user)
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        logger.info("Received sign-up request for username: {}", signUpRequest.getUsername());

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            logger.warn("Username {} is already taken", signUpRequest.getUsername());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            logger.warn("Email {} is already in use", signUpRequest.getEmail());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAddress(),
                signUpRequest.getPhonenumber(),
                signUpRequest.getGender(),
                signUpRequest.getState(),
                signUpRequest.getCountry(),
                signUpRequest.getPostalcode()
        );

        user.setRoles(getRoles(signUpRequest));
        userRepository.save(user);

        logger.info("User {} registered successfully", signUpRequest.getUsername());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // Get roles for the user
    public Set<Role> getRoles(SignupRequest signupRequest) {
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> {
                        logger.error("Error: Role ROLE_USER is not found.");
                        return new RuntimeException("Error: Role is not found.");
                    });
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> {
                                    logger.error("Error: Role ROLE_ADMIN is not found.");
                                    return new RuntimeException("Error: Role is not found.");
                                });
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> {
                                    logger.error("Error: Role ROLE_USER is not found.");
                                    return new RuntimeException("Error: Role is not found.");
                                });
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }
}
