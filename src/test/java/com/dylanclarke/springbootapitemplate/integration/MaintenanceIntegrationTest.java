package com.dylanclarke.springbootapitemplate.integration;

import com.dylanclarke.springbootapitemplate.dto.MaintenanceRequestDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MaintenanceIntegrationTest extends BaseIntegrationTest {


    // =========================================================
    // CREATE TESTS
    // =========================================================

    @Test
    @DisplayName("Should create maintenance")
    void shouldCreateMaintenance() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long vehicleId = createVehicle(token);

        MaintenanceRequestDTO request = createMaintenanceRequest(vehicleId);


        // Act

        mockMvc.perform(post("/api/maintenance")
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
                .andExpect(jsonPath("$.data.vehicleId")
                        .value(vehicleId));
    }

    @Test
    @DisplayName("Should reject maintenance without description")
    void shouldRejectMaintenanceWithoutDescription() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long vehicleId = createVehicle(token);

        MaintenanceRequestDTO request = createMaintenanceRequest(vehicleId);

        request.setDescription(null);


        // Act

        mockMvc.perform(post("/api/maintenance")
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
    @DisplayName("Should return maintenance by id")
    void shouldReturnMaintenanceById() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long vehicleId = createVehicle(token);

        Long maintenanceId = createMaintenance(token, vehicleId);


        // Act

        mockMvc.perform(get("/api/maintenance/" + maintenanceId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true))
                .andExpect(jsonPath("$.data.id")
                        .value(maintenanceId));
    }

    @Test
    @DisplayName("Should reject missing maintenance")
    void shouldRejectMissingMaintenance() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long missingMaintenanceId = 99999L;


        // Act

        mockMvc.perform(get("/api/maintenance/" + missingMaintenanceId)
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
    @DisplayName("Should update maintenance")
    void shouldUpdateMaintenance() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long vehicleId = createVehicle(token);

        Long maintenanceId = createMaintenance(token, vehicleId);

        MaintenanceRequestDTO request = createMaintenanceRequest(vehicleId);

        request.setDescription("Updated Oil Change");


        // Act

        mockMvc.perform(put("/api/maintenance/" + maintenanceId)
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
                .andExpect(jsonPath("$.data.description")
                        .value("Updated Oil Change"));
    }

    // =========================================================
    // DELETE TESTS
    // =========================================================

    @Test
    @DisplayName("Should delete maintenance record")
    void shouldDeleteMaintenance() throws Exception {

        // Arrange

        String token = authenticate("maintenanceuser");

        Long vehicleId = createVehicle(token);

        Long maintenanceId = createMaintenance(token, vehicleId);


        // Act

        mockMvc.perform(delete("/api/maintenance/" + maintenanceId)
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