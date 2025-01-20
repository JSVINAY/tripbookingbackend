package com.training.tripbooking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.tripbooking.model.Booking;
import com.training.tripbooking.model.Passenger;
import com.training.tripbooking.model.StayType;
import com.training.tripbooking.model.UserEntity;
import com.training.tripbooking.service.BookingService;

@SpringBootTest // Change this annotation
@AutoConfigureMockMvc // This configures MockMvc automatically with Spring Boot Test
class BookingControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private BookingService bookingService;

	@InjectMocks
	private BookingController bookingController;

	private Booking booking1;
	private Booking booking2;

	@BeforeEach
	void setUp() {
		// Initialize MockMvc with the controller and mock service
		mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

		// Create Passenger objects
		Passenger passenger1 = new Passenger(1L, "John Smith", 30, "Male", "Vegetarian", "A12345", "Passport");
		Passenger passenger2 = new Passenger(2L, "Jane Smith", 28, "Female", "Non-Vegetarian", "B12345", "Passport");

		// Create Booking objects with list of passengers, stay type, and other details
		booking1 = new Booking();
		booking1.setBookingId(1L);
		booking1.setDestination("Destination1");
		booking1.setPassengers(Arrays.asList(passenger1, passenger2));
		booking1.setStayType(StayType.HOTEL);
		booking1.setPickupLocation("Pickup Location 1");
		booking1.setDropLocation("Drop Location 1");
		booking1.setUser(new UserEntity());

		booking2 = new Booking();
		booking2.setBookingId(2L);
		booking2.setDestination("Destination2");
		booking2.setPassengers(Arrays.asList(passenger1));
		booking2.setStayType(StayType.RESORT);
		booking2.setPickupLocation("Pickup Location 2");
		booking2.setDropLocation("Drop Location 2");
		booking2.setUser(new UserEntity());
	}

	@Test
	void testCreateBooking() throws Exception {
		when(bookingService.createBooking(any(Booking.class))).thenReturn(booking1);

		mockMvc.perform(
				post("/api/bookings/insert").contentType(MediaType.APPLICATION_JSON).content(asJsonString(booking1)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.destination").value("Destination1"))
				.andExpect(jsonPath("$.pickupLocation").value("Pickup Location 1"))
				.andExpect(jsonPath("$.dropLocation").value("Drop Location 1"))
				.andExpect(jsonPath("$.passengers[0].name").value("John Smith"))
				.andExpect(jsonPath("$.passengers[1].name").value("Jane Smith"));
	}

	@Test
	void testGetAllBookings() throws Exception {
		List<Booking> bookings = Arrays.asList(booking1, booking2);
		when(bookingService.getAllBookings()).thenReturn(bookings);

		mockMvc.perform(get("/api/bookings/all/packagebooking").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].destination").value("Destination1"))
				.andExpect(jsonPath("$[1].destination").value("Destination2"));
	}

	@Test
	void testGetBookingByIdSuccess() throws Exception {
		when(bookingService.getBookingById(1L)).thenReturn(booking1);

		mockMvc.perform(get("/api/bookings/view/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.destination").value("Destination1"))
				.andExpect(jsonPath("$.pickupLocation").value("Pickup Location 1"))
				.andExpect(jsonPath("$.dropLocation").value("Drop Location 1"));
	}

	@Test
	void testGetBookingByIdNotFound() throws Exception {
		when(bookingService.getBookingById(1L)).thenReturn(null);

		mockMvc.perform(get("/api/bookings/view/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteBookingSuccess() throws Exception {
		doNothing().when(bookingService).deleteBooking(1L);

		mockMvc.perform(delete("/api/bookings/delete/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(bookingService, times(1)).deleteBooking(1L);
	}

	@Test
	void testDeleteBookingNotFound() throws Exception {
		doThrow(new RuntimeException("Booking not found")).when(bookingService).deleteBooking(1L);

		mockMvc.perform(delete("/api/bookings/delete/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(bookingService, times(1)).deleteBooking(1L);
	}

	private String asJsonString(final Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
