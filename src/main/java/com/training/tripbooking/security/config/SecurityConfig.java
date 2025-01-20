package com.training.tripbooking.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.training.tripbooking.security.jwt.AuthEntryPointJwt;
import com.training.tripbooking.security.jwt.AuthTokenFilter;
import com.training.tripbooking.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**" };

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF and allow stateless session
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Handle unauthorized requests
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))

            // Add custom JWT filter
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)

            // Configure authorization rules
            .authorizeHttpRequests(authorize -> authorize
                // Allow preflight (OPTIONS) requests
                .requestMatchers("/**").permitAll()

                // Permit public endpoints
                .requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()

                // Protect specific endpoints
                .requestMatchers("/api/bookings/insert").hasRole("USER")
                .requestMatchers("/api/bookings/delete").hasRole("ADMIN")
                .requestMatchers("/api/payment/makepayment").hasRole("USER")
                .requestMatchers("/api/bookings/view").permitAll()

                // Default to authenticated for all other endpoints
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
