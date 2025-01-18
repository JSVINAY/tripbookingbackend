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
	public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/auth/**","/swagger-ui/**","/v3/api-docs/**"};

	
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
						/*
						 * .requestMatchers("/greet").hasRole("USER")
						 * .requestMatchers("/admingreet").hasRole("ADMIN")
						 */
                	
                	//Bookings Path for user
                	//.requestMatchers("/api/bookings/insert").hasRole("USER")
                	.requestMatchers("/api/bookings/insert").hasRole("USER")
                	.requestMatchers("/api/bookings/view").permitAll()

                	//Bookings Path for admin
                	.requestMatchers("/api/bookings/delete").hasRole("ADMIN")
                	.requestMatchers("/api/bookings/all/packagebooking").hasRole("USER")
                	

                	
                	
                	//payment securitycode
                	.requestMatchers("/api/payment/makepayment").hasRole("USER")
                	
                	
                	
                	
                	
   
                	                	
						/*
						 * .requestMatchers("/helloworld/hello").authenticated()
						 * .requestMatchers("/bookrestapi/bookapi/book/new").hasRole("ADMIN")
						 * .requestMatchers("/bookrestapi/bookapi/book/delete/**").hasRole("ADMIN")
						 * .requestMatchers("/bookrestapi/bookapi/book/name/**").permitAll()
						 * .requestMatchers("/bookrestapi/bookapi/book/books").permitAll()
						 * .requestMatchers("/bookrestapi/bookapi/test").authenticated()
						 * .requestMatchers("/api/customer/delete/**").hasRole("ADMIN")
						 * .requestMatchers("/api/customer/update").hasRole("ADMIN")
						 * .requestMatchers("/api/customer/new").hasRole("ADMIN")
						 * .requestMatchers("/api/customer/get/**").permitAll()
						 * .requestMatchers("/api/customer/customers").hasAnyRole("USER","ADMIN")
						 * //.requestMatchers("/api/customer/**").permitAll()
						 */                	.requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()
                   //.anyRequest().authenticated()
            )
            // Enable HTTP Basic Authentication
            //.httpBasic(Customizer.withDefaults())
            // Disable CSRF for simplicity (not recommended for production)
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	        return http.build();
    }

	
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt password encoder
        return new BCryptPasswordEncoder();
    }
   
    
}

