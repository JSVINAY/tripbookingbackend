package com.training.tripbooking.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.tripbooking.model.TripPackage;
import com.training.tripbooking.service.TripPackageSevice;

public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TripPackageSevice tripPackageService;

    @InjectMocks
    private AdminController adminController;

    private TripPackage tripPackage;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Build MockMvc with adminController
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        // Initialize a sample TripPackage object
        tripPackage = new TripPackage("Destination", "image.jpg", 1000.0, 7, java.time.LocalDate.now());
    }

    @Test
    void testGetAllPackages() throws Exception {
        // Mock the service to return a list of trip packages
        doReturn(List.of(tripPackage)).when(tripPackageService).getAllPackages();

        mockMvc.perform(get("/admin/packages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].destination").value("Destination"));
    }

    @Test
    void testGetPackageById_Success() throws Exception {
        // Mock the service to return a package for a valid ID
        doReturn(tripPackage).when(tripPackageService).getPackageById(1L);

        mockMvc.perform(get("/admin/packages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.destination").value("Destination"));
    }

    @Test
    void testGetPackageById_NotFound() throws Exception {
        // Mock the service to return null when the ID is not found
        doReturn(null).when(tripPackageService).getPackageById(999L);

        mockMvc.perform(get("/admin/packages/999"))
                .andExpect(status().isNotFound());
    }

	/*
	 * @Test void testCreatePackage() throws Exception { // Mock the service to
	 * return the created trip package
	 * doReturn(tripPackage).when(tripPackageService).createPackage(tripPackage);
	 * 
	 * // Convert tripPackage object to JSON ObjectMapper objectMapper = new
	 * ObjectMapper(); String jsonContent =
	 * objectMapper.writeValueAsString(tripPackage);
	 * 
	 * mockMvc.perform(post("/admin/packages")
	 * .contentType(MediaType.APPLICATION_JSON) .content(jsonContent))
	 * .andExpect(status().isCreated())
	 * .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	 * .andExpect(jsonPath("$.destination").value("Destination")); }
	 */

	/*
	 * @Test void testUpdatePackage_Success() throws Exception { // Mock the service
	 * to return the updated package
	 * doReturn(tripPackage).when(tripPackageService).updatePackage(1L,
	 * tripPackage);
	 * 
	 * // Convert tripPackage object to JSON ObjectMapper objectMapper = new
	 * ObjectMapper(); String jsonContent =
	 * objectMapper.writeValueAsString(tripPackage);
	 * 
	 * mockMvc.perform(put("/admin/packages/1")
	 * .contentType(MediaType.APPLICATION_JSON) .content(jsonContent))
	 * .andExpect(status().isOk())
	 * .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	 * .andExpect(jsonPath("$.destination").value("Destination")); }
	 */

	/*
	 * @Test void testUpdatePackage_NotFound() throws Exception { // Mock the
	 * service to return null when the package is not found
	 * doReturn(null).when(tripPackageService).updatePackage(999L, tripPackage);
	 * 
	 * // Convert tripPackage object to JSON ObjectMapper objectMapper = new
	 * ObjectMapper(); String jsonContent =
	 * objectMapper.writeValueAsString(tripPackage);
	 * 
	 * mockMvc.perform(put("/admin/packages/999")
	 * .contentType(MediaType.APPLICATION_JSON) .content(jsonContent))
	 * .andExpect(status().isNotFound()); }
	 */

	/*
	 * @Test void testDeletePackage() throws Exception { // Mock the service to
	 * delete the package successfully
	 * doReturn(true).when(tripPackageService).deletePackage(1L);
	 * 
	 * mockMvc.perform(delete("/admin/packages/1"))
	 * .andExpect(status().isNoContent());
	 * 
	 * verify(tripPackageService).deletePackage(1L); }
	 */
}
