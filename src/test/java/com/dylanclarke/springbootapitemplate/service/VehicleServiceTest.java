package com.dylanclarke.springbootapitemplate.service;

import com.dylanclarke.springbootapitemplate.dto.VehicleRequestDTO;
import com.dylanclarke.springbootapitemplate.dto.VehicleResponseDTO;
import com.dylanclarke.springbootapitemplate.exception.ResourceNotFoundException;
import com.dylanclarke.springbootapitemplate.exception.ValidationException;
import com.dylanclarke.springbootapitemplate.model.Vehicle;
import com.dylanclarke.springbootapitemplate.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService(repository);
    }

    @Test
    void addVehicle_shouldCreateVehicleSuccessfully() {

        // Arrange
        VehicleRequestDTO request = createValidRequest();

        Vehicle savedVehicle = createVehicle();
        savedVehicle.setId(1L);

        when(repository.save(any(Vehicle.class)))
                .thenReturn(savedVehicle);

        // Act
        VehicleResponseDTO result =
                vehicleService.addVehicle(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Vehicle", result.getTitle());
        assertEquals("1HGCM82633A123456", result.getVin());

        verify(repository).save(any(Vehicle.class));
    }

    @Test
    void addVehicle_shouldDefaultMaintenanceAlertsToFalse() {

        // Arrange
        VehicleRequestDTO request = createValidRequest();
        request.setMaintenanceAlertsEnabled(null);

        Vehicle savedVehicle = createVehicle();
        savedVehicle.setId(1L);

        when(repository.save(any(Vehicle.class)))
                .thenReturn(savedVehicle);

        ArgumentCaptor<Vehicle> captor =
                ArgumentCaptor.forClass(Vehicle.class);

        // Act
        vehicleService.addVehicle(request);

        // Assert
        verify(repository).save(captor.capture());

        assertFalse(
                captor.getValue().isMaintenanceAlertsEnabled()
        );
    }

    @Test
    void addVehicle_shouldRejectYearBefore1886() {

        // Arrange
        VehicleRequestDTO request = createValidRequest();
        request.setYear(1885);

        // Act
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> vehicleService.addVehicle(request)
        );

        // Assert
        assertEquals(
                "Year must be between 1886 and " + LocalDate.now().getYear(),
                exception.getMessage()
        );
        assertEquals("vehicleYear", exception.getFieldName());
        assertEquals(1885, exception.getFieldValue());

        verify(repository, never()).save(any());
    }

    @Test
    void addVehicle_shouldRejectFutureYear() {

        // Arrange
        int futureYear = LocalDate.now().getYear() + 1;

        VehicleRequestDTO request = createValidRequest();
        request.setYear(futureYear);

        // Act
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> vehicleService.addVehicle(request)
        );

        // Assert
        assertEquals(
                "Year must be between 1886 and " + LocalDate.now().getYear(),
                exception.getMessage()
        );
        assertEquals("vehicleYear", exception.getFieldName());
        assertEquals(futureYear, exception.getFieldValue());

        verify(repository, never()).save(any());
    }

    @Test
    void addVehicle_shouldRejectMissingStartDate() {

        // Arrange
        VehicleRequestDTO request = createValidRequest();
        request.setStartDate(null);

        // Act & Assert
        assertThrows(
                ValidationException.class,
                () -> vehicleService.addVehicle(request)
        );

        verify(repository, never()).save(any());
    }

    @Test
    void addVehicle_shouldRejectEndDateBeforeStartDate() {

        // Arrange
        VehicleRequestDTO request = createValidRequest();

        request.setStartDate(
                LocalDate.of(2025, 1, 1)
        );

        request.setEndDate(
                LocalDate.of(2024, 12, 31)
        );

        // Act & Assert
        assertThrows(
                ValidationException.class,
                () -> vehicleService.addVehicle(request)
        );

        verify(repository, never()).save(any());
    }

    @Test
    void updateVehicle_shouldThrowWhenVehicleDoesNotExist() {

        // Arrange
        Long vehicleId = 999L;
        VehicleRequestDTO request = createValidRequest();

        when(repository.findById(vehicleId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> vehicleService.updateVehicle(
                        vehicleId,
                        request
                )
        );

        verify(repository, never()).save(any());
    }

    private VehicleRequestDTO createValidRequest() {

        VehicleRequestDTO request =
                new VehicleRequestDTO();

        request.setTitle("Test Vehicle");
        request.setVin("1HGCM82633A123456");
        request.setLicensePlate("TEST123");
        request.setMake("Honda");
        request.setModel("Accord");
        request.setYear(2020);
        request.setLocation("Test Location");
        request.setMaintenanceAlertsEnabled(true);
        request.setStartDate(
                LocalDate.of(2024, 1, 1)
        );
        request.setEndDate(null);

        return request;
    }

    private Vehicle createVehicle() {

        Vehicle vehicle = new Vehicle();

        vehicle.setTitle("Test Vehicle");
        vehicle.setVin("1HGCM82633A123456");
        vehicle.setLicensePlate("TEST123");
        vehicle.setMake("Honda");
        vehicle.setModel("Accord");
        vehicle.setVehicleYear(2020);
        vehicle.setLocation("Test Location");
        vehicle.setMaintenanceAlertsEnabled(true);
        vehicle.setStartDate(
                LocalDate.of(2024, 1, 1)
        );
        vehicle.setEndDate(null);

        return vehicle;
    }

    @Test
    void updateVehicle_shouldUpdateVehicleSuccessfully() {

        // Arrange
        Long vehicleId = 1L;

        Vehicle existingVehicle = createVehicle();
        existingVehicle.setId(vehicleId);

        VehicleRequestDTO request = createValidRequest();
        request.setTitle("Updated Vehicle");
        request.setYear(2022);
        request.setMaintenanceAlertsEnabled(false);

        when(repository.findById(vehicleId))
                .thenReturn(Optional.of(existingVehicle));

        when(repository.save(any(Vehicle.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VehicleResponseDTO result =
                vehicleService.updateVehicle(vehicleId, request);

        // Assert
        assertNotNull(result);
        assertEquals(vehicleId, result.getId());
        assertEquals("Updated Vehicle", result.getTitle());
        assertEquals(2022, result.getVehicleYear());
        assertFalse(result.getMaintenanceAlertsEnabled());

        verify(repository).findById(vehicleId);
        verify(repository).save(existingVehicle);
    }

    @Test
    void getVehicleById_shouldReturnVehicleSuccessfully() {

        // Arrange
        Long vehicleId = 1L;

        Vehicle vehicle = createVehicle();
        vehicle.setId(vehicleId);

        when(repository.findById(vehicleId))
                .thenReturn(Optional.of(vehicle));

        // Act
        VehicleResponseDTO result =
                vehicleService.getVehicleById(vehicleId);

        // Assert
        assertNotNull(result);
        assertEquals(vehicleId, result.getId());
        assertEquals("Test Vehicle", result.getTitle());
        assertEquals("1HGCM82633A123456", result.getVin());
        assertEquals("TEST123", result.getLicensePlate());
        assertEquals("Honda", result.getMake());
        assertEquals("Accord", result.getModel());
        assertEquals(2020, result.getVehicleYear());
        assertEquals("Test Location", result.getLocation());
        assertTrue(result.getMaintenanceAlertsEnabled());
        assertEquals(
                LocalDate.of(2024, 1, 1),
                result.getStartDate()
        );
        assertNull(result.getEndDate());

        verify(repository).findById(vehicleId);
    }

    @Test
    void deleteVehicle_shouldDeleteVehicleSuccessfully() {

        // Arrange
        Long vehicleId = 1L;

        Vehicle vehicle = createVehicle();
        vehicle.setId(vehicleId);

        when(repository.findById(vehicleId))
                .thenReturn(Optional.of(vehicle));

        // Act
        vehicleService.deleteVehicle(vehicleId);

        // Assert
        verify(repository).findById(vehicleId);
        verify(repository).delete(vehicle);
    }

    @Test
    void getAllVehicles_shouldReturnPaginatedVehicles() {

        // Arrange
        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Vehicle> vehiclePage =
                new PageImpl<>(List.of(vehicle), pageable, 1);

        when(repository.findAll(pageable))
                .thenReturn(vehiclePage);

        // Act
        Page<VehicleResponseDTO> result =
                vehicleService.getAllVehicles(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        VehicleResponseDTO dto =
                result.getContent().get(0);

        assertEquals(1L, dto.getId());
        assertEquals("Test Vehicle", dto.getTitle());
        assertEquals("1HGCM82633A123456", dto.getVin());
        assertEquals("Honda", dto.getMake());
        assertEquals("Accord", dto.getModel());
        assertEquals(2020, dto.getVehicleYear());
        assertEquals("Test Location", dto.getLocation());

        verify(repository).findAll(pageable);
    }

    @Test
    void searchVehicles_shouldReturnPaginatedVehicles() {

        // Arrange
        String query = "Honda";

        Vehicle vehicle = createVehicle();
        vehicle.setId(1L);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Vehicle> vehiclePage =
                new PageImpl<>(List.of(vehicle), pageable, 1);

        when(repository.searchVehicles(query, pageable))
                .thenReturn(vehiclePage);

        // Act
        Page<VehicleResponseDTO> result =
                vehicleService.searchVehicles(query, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        VehicleResponseDTO dto =
                result.getContent().get(0);

        assertEquals(1L, dto.getId());
        assertEquals("Test Vehicle", dto.getTitle());
        assertEquals("1HGCM82633A123456", dto.getVin());
        assertEquals("Honda", dto.getMake());
        assertEquals("Accord", dto.getModel());
        assertEquals(2020, dto.getVehicleYear());
        assertEquals("Test Location", dto.getLocation());

        verify(repository).searchVehicles(query, pageable);
    }
}