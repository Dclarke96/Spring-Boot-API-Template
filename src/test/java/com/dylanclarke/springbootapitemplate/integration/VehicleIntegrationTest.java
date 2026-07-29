package com.dylanclarke.springbootapitemplate.integration;

import com.dylanclarke.springbootapitemplate.dto.VehicleRequestDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class VehicleIntegrationTest extends BaseIntegrationTest {


    // =========================================================
    // CREATE TESTS
    // =========================================================

    @Test
    @DisplayName("Should create a vehicle")
    void shouldCreateVehicle() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        VehicleRequestDTO request = createVehicleRequest();


        // Act

        mockMvc.perform(post("/api/vehicles")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))


                // Assert

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success")
                        .value(true))
                .andExpect(jsonPath("$.data.title")
                        .value("Fleet Truck"));
    }

    @Test
    @DisplayName("Should reject vehicle without title")
    void shouldRejectVehicleWithoutTitle() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        VehicleRequestDTO request = createVehicleRequest();

        request.setTitle(null);


        // Act

        mockMvc.perform(post("/api/vehicles")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))


                // Assert

                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // RETRIEVE TESTS
    // =========================================================

    @Test
    @DisplayName("Should return vehicle by id")
    void shouldReturnVehicleById() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        Long vehicleId = createVehicle(token);


        // Act

        mockMvc.perform(get("/api/vehicles/" + vehicleId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true))
                .andExpect(jsonPath("$.data.id")
                        .value(vehicleId));
    }

    @Test
    @DisplayName("Should reject request for missing vehicle")
    void shouldRejectMissingVehicle() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        Long missingVehicleId = 99999L;


        // Act

        mockMvc.perform(get("/api/vehicles/" + missingVehicleId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isNotFound());
    }

    // =========================================================
    // UPDATE TESTS
    // =========================================================

    @Test
    @DisplayName("Should update a vehicle")
    void shouldUpdateVehicle() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        Long vehicleId = createVehicle(token);

        VehicleRequestDTO request = createVehicleRequest();

        request.setTitle("Updated Fleet Truck");


        // Act

        mockMvc.perform(put("/api/vehicles/" + vehicleId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))


                // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true))
                .andExpect(jsonPath("$.data.title")
                        .value("Updated Fleet Truck"));
    }

    // =========================================================
    // DELETE TESTS
    // =========================================================

    @Test
    @DisplayName("Should delete a vehicle")
    void shouldDeleteVehicle() throws Exception {

        // Arrange

        String token = authenticate("vehicleuser");

        Long vehicleId = createVehicle(token);


        // Act

        mockMvc.perform(delete("/api/vehicles/" + vehicleId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true));
    }

}