package com.dylanclarke.springbootapitemplate.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.dylanclarke.springbootapitemplate.dto.VehicleRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;


class ExceptionHandlingIntegrationTest extends BaseIntegrationTest {


        @Test
        @DisplayName("Should return standardized error response when authentication is missing")
        void shouldReturn401WhenAuthenticationIsMissing() throws Exception {

                // Arrange

                String endpoint = "/api/vehicles";


                // Act

                mockMvc.perform(get(endpoint))


                        // Assert

                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.status")
                                .value(401))
                        .andExpect(jsonPath("$.error")
                                .value("Unauthorized"))
                        .andExpect(jsonPath("$.message")
                                .value("Invalid authentication credentials"))
                        .andExpect(jsonPath("$.path")
                                .exists())
                        .andExpect(jsonPath("$.timestamp")
                                .exists())
                        .andExpect(jsonPath("$.traceId")
                                .exists());
        }


        @Test
        @DisplayName("Should return standardized error response when endpoint does not exist")
        void shouldReturn404WhenEndpointDoesNotExist() throws Exception {

        // Arrange

        String token = authenticate("missingendpointuser");

        String endpoint = "/api/does-not-exist";


        // Act

        mockMvc.perform(get(endpoint)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))


                // Assert

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404))
                .andExpect(jsonPath("$.error")
                        .value("Endpoint Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("The requested endpoint was not found"))
                .andExpect(jsonPath("$.path")
                        .value(endpoint))
                .andExpect(jsonPath("$.traceId")
                        .exists());
        }

        @Test
        @DisplayName("Should return standardized error response when request validation fails")
        void shouldReturn400WhenRequestValidationFails() throws Exception {

        // Arrange

        String token = authenticate("validationuser");

        String endpoint = "/api/vehicles";

        String json = """
        {
                "title": null,
                "vin": "VIN123",
                "licensePlate": "ABC123",
                "make": "Ford",
                "model": "F150",
                "year": null,
                "location": "Yard",
                "maintenanceAlertsEnabled": true,
                "startDate": "2026-08-04",
                "endDate": "2027-08-04"
        }
        """;


        // Act

        mockMvc.perform(post(endpoint)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))


                // Assert

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Validation Failed"))
                .andExpect(jsonPath("$.message")
                        .value("Request validation failed"))
                .andExpect(jsonPath("$.path")
                        .value(endpoint))
                .andExpect(jsonPath("$.timestamp")
                        .exists())
                .andExpect(jsonPath("$.traceId")
                        .exists())
                .andExpect(jsonPath("$.fieldErrors")
                        .isArray())
                .andExpect(jsonPath("$.fieldErrors[?(@.field == 'title')]")
                        .isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors[?(@.field == 'year')]")
                        .isNotEmpty());
        }


        @Test
        @DisplayName("Should return standardized error response when business validation fails")
        void shouldReturn400WhenBusinessValidationFails() throws Exception {

                // Arrange

                String token = authenticate("bizvalidate");

                VehicleRequestDTO request = new VehicleRequestDTO();

                request.setTitle("Validation Truck");
                request.setVin("VIN123");
                request.setLicensePlate("ABC123");
                request.setMake("Ford");
                request.setModel("F150");
                request.setYear(2024);
                request.setLocation("Yard");
                request.setMaintenanceAlertsEnabled(true);
                request.setStartDate(LocalDate.now());
                request.setEndDate(LocalDate.now().minusDays(1));


                // Act

                mockMvc.perform(post("/api/vehicles")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request)))


                        // Assert

                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status")
                                .value(400))
                        .andExpect(jsonPath("$.error")
                                .value("Validation Failed"))
                        .andExpect(jsonPath("$.message")
                                .value("End date cannot be before start date"))
                        .andExpect(jsonPath("$.path")
                                .value("/api/vehicles"))
                        .andExpect(jsonPath("$.timestamp")
                                .exists())
                        .andExpect(jsonPath("$.traceId")
                                .exists());
        }

}