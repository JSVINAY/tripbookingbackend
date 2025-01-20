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

    public final static String[] PUBLIC_REQUEST_MATCHERS = { 
        "/api/auth/**", 
        "/swagger-ui/**", 
        "/v3/api-docs/**", 
        "/h2-console/**" 
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Authorize requests
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints (allow unrestricted access)
                .requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()

                // Role-based access for /admin/** paths
                .requestMatchers("/admin/**").hasRole("ADMIN") // Only accessible by ADMIN
                .requestMatchers("/admin/packages/{id}").hasRole("ADMIN")
                .requestMatchers("/admin/packages").hasRole("ADMIN")
                .requestMatchers("/admin/packages/{id}").hasRole("ADMIN")
                // Additional endpoint restrictions for USER and ADMIN
                .requestMatchers("/api/bookings/insert").hasRole("USER")
                .requestMatchers("/api/bookings/view").permitAll()
                .requestMatchers("/api/bookings/delete").hasRole("ADMIN")
                .requestMatchers("/api/bookings/all/packagebooking").hasRole("USER")
                .requestMatchers("/api/payment/makepayment").hasRole("USER")
            )
            // Enable HTTP Basic Authentication
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // Allow H2 console access
        http
            .headers().frameOptions().sameOrigin(); // This allows iframe access to H2 console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
