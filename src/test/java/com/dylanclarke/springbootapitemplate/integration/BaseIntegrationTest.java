package com.dylanclarke.springbootapitemplate.integration;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.dylanclarke.springbootapitemplate.repository.MaintenanceRepository;
import com.dylanclarke.springbootapitemplate.repository.UserRepository;
import com.dylanclarke.springbootapitemplate.repository.VehicleRepository;
import com.dylanclarke.springbootapitemplate.dto.VehicleRequestDTO;
import com.dylanclarke.springbootapitemplate.dto.MaintenanceRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Base class for all integration tests.
 *
 * Provides:
 * - Shared Spring Boot test configuration
 * - Testcontainers PostgreSQL database
 * - Common authentication helpers
 * - Common vehicle helpers
 * - Common maintenance helpers
 *
 * Individual integration tests should extend this class and focus only
 * on the behavior being tested.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIntegrationTest {


    // =========================================================
    // TEST DATABASE
    // =========================================================

    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16")
                    .withDatabaseName("springboot_template_test")
                    .withUsername("postgres")
                    .withPassword("postgres");

    static {
        postgres.start();
    }


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
    * Converts an object into a JSON string for HTTP requests.
    */
    protected String toJson(
            Object object
    ) throws Exception {

        return objectMapper.writeValueAsString(object);
    }

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected VehicleRepository vehicleRepository;

    @Autowired
    protected MaintenanceRepository maintenanceRepository;

    @BeforeEach
    protected void cleanDatabase() {

        maintenanceRepository.deleteAll();

        vehicleRepository.deleteAll();

        userRepository.deleteAll();
    }


    // =========================================================
    // AUTHENTICATION HELPERS
    // =========================================================

    /**
     * Registers a user with default test credentials.
     */
    protected void register(
            String username
    ) throws Exception {

        register(
                username,
                username + "@example.com",
                "password"
        );
    }


    /**
     * Registers a user with explicit credentials.
     */
    protected void register(
            String username,
            String email,
            String password
    ) throws Exception {

        String json = """
        {
          "username":"%s",
          "email":"%s",
          "password":"%s"
        }
        """.formatted(
                username,
                email,
                password
        );

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }


    protected String login(
            String username
    ) throws Exception {

        String json = """
        {
          "username":"%s",
          "password":"password"
        }
        """.formatted(username);


        String response =
                mockMvc.perform(post("/api/auth/login")
                                .contentType("application/json")
                                .content(json))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();


        JsonNode node = objectMapper.readTree(response);

        return node.get("data").asText();
    }

    /**
    * Registers and authenticates a user using the default test credentials.
    *
    * @param username Username to register and authenticate.
    * @return JWT authentication token.
    */
    protected String authenticate(
            String username
    ) throws Exception {

        register(username);

        return login(username);
    }


    // =========================================================
    // VEHICLE HELPERS
    // =========================================================

    /**
    * Creates a valid vehicle request with default test data.
    */
    protected VehicleRequestDTO createVehicleRequest() {

        VehicleRequestDTO request = new VehicleRequestDTO();

        request.setTitle("Fleet Truck");
        request.setVin("VIN123");
        request.setLicensePlate("ABC123");
        request.setMake("Ford");
        request.setModel("F150");
        request.setYear(2024);
        request.setLocation("Yard");
        request.setMaintenanceAlertsEnabled(true);
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusYears(1));

        return request;
    }

    protected Long createVehicle(
            String token
    ) throws Exception {

        return createVehicle(
                token,
                "Fleet Truck",
                LocalDate.now(),
                LocalDate.now().plusYears(1)
        );
    }


    protected Long createVehicle(
            String token,
            String title
    ) throws Exception {

        return createVehicle(
                token,
                title,
                LocalDate.now(),
                LocalDate.now().plusYears(1)
        );
    }


    protected Long createVehicle(
            String token,
            String title,
            LocalDate startDate,
            LocalDate endDate
    ) throws Exception {

        VehicleRequestDTO request = createVehicleRequest();

        request.setTitle(title);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        String response =
                mockMvc.perform(post("/api/vehicles")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                                .contentType("application/json")
                                .content(toJson(request)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        JsonNode node = objectMapper.readTree(response);

        return node.get("data")
                .get("id")
                .asLong();
    }


    protected void getVehicle(
            String token,
            Long vehicleId
    ) throws Exception {

        mockMvc.perform(get("/api/vehicles/" + vehicleId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }


    // =========================================================
    // MAINTENANCE HELPERS
    // =========================================================

    /**
    * Creates a valid maintenance request with default test data.
    */
    protected MaintenanceRequestDTO createMaintenanceRequest(
            Long vehicleId
    ) {

        MaintenanceRequestDTO request = new MaintenanceRequestDTO();

        request.setVehicleId(vehicleId);
        request.setDescription("Oil Change");
        request.setDate(LocalDate.now().plusDays(1));
        request.setCost(125.50);

        return request;
    }

    protected Long createMaintenance(
            String token,
            Long vehicleId
    ) throws Exception {

        MaintenanceRequestDTO request = createMaintenanceRequest(vehicleId);

        String response =
                mockMvc.perform(post("/api/maintenance")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                                .contentType("application/json")
                                .content(toJson(request)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        JsonNode node = objectMapper.readTree(response);

        return node.get("data")
                .get("id")
                .asLong();
    }


    protected void getMaintenance(
            String token,
            Long maintenanceId
    ) throws Exception {

        mockMvc.perform(get("/api/maintenance/" + maintenanceId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }


    protected void updateMaintenance(
            String token,
            Long maintenanceId,
            Long vehicleId
    ) throws Exception {


        LocalDate updatedDate = LocalDate.now().plusDays(2);


        String json = """
        {
          "vehicleId":%d,
          "description":"Updated Oil Change",
          "date":"%s",
          "cost":150.00
        }
        """.formatted(
                vehicleId,
                updatedDate
        );


        mockMvc.perform(put("/api/maintenance/" + maintenanceId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }


    protected void deleteMaintenance(
            String token,
            Long maintenanceId
    ) throws Exception {


        mockMvc.perform(delete("/api/maintenance/" + maintenanceId)
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}