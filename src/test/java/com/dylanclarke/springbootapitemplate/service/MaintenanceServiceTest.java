package com.dylanclarke.springbootapitemplate.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

import com.dylanclarke.springbootapitemplate.dto.MaintenanceRequestDTO;
import com.dylanclarke.springbootapitemplate.dto.MaintenanceResponseDTO;
import com.dylanclarke.springbootapitemplate.exception.ValidationException;
import com.dylanclarke.springbootapitemplate.exception.ResourceNotFoundException;
import com.dylanclarke.springbootapitemplate.model.MaintenanceRecord;
import com.dylanclarke.springbootapitemplate.model.Vehicle;
import com.dylanclarke.springbootapitemplate.repository.MaintenanceRepository;
import com.dylanclarke.springbootapitemplate.repository.VehicleRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    private MaintenanceService maintenanceService;


    @BeforeEach
    void setUp() {
        maintenanceService =
                new MaintenanceService(
                        maintenanceRepository,
                        vehicleRepository
                );
    }


    // =========================================================
    // CREATE
    // =========================================================

    @Test
    void addMaintenance_shouldCreateMaintenanceSuccessfully() {

        // Arrange
        MaintenanceRequestDTO request =
                createValidRequest();

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord savedRecord =
                createMaintenanceRecord(vehicle);

        savedRecord.setId(1L);

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        when(maintenanceRepository.save(any(MaintenanceRecord.class)))
                .thenReturn(savedRecord);

        // Act
        MaintenanceResponseDTO result =
                maintenanceService.addMaintenance(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getVehicleId());
        assertEquals(
                "Oil change",
                result.getDescription()
        );
        assertEquals(
                LocalDate.of(2025, 1, 15),
                result.getDate()
        );
        assertEquals(
                75.00,
                result.getCost()
        );

        verify(vehicleRepository).findById(1L);
        verify(maintenanceRepository)
                .save(any(MaintenanceRecord.class));
    }


    @Test
    void addMaintenance_shouldThrowWhenVehicleDoesNotExist() {

        // Arrange
        MaintenanceRequestDTO request =
                createValidRequest();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.addMaintenance(request)
        );

        verify(vehicleRepository).findById(1L);
        verify(maintenanceRepository, never())
                .save(any());
    }


    @Test
    void addMaintenance_shouldRejectDateBeforeVehicleStartDate() {

        // Arrange
        MaintenanceRequestDTO request =
                createValidRequest();

        request.setDate(
                LocalDate.of(2023, 12, 31)
        );

        Vehicle vehicle =
                createVehicle();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        // Act
        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> maintenanceService.addMaintenance(request)
                );

        // Assert
        assertEquals(
                "Maintenance date cannot occur before vehicle start date",
                exception.getMessage()
        );
        assertEquals(
                "date",
                exception.getFieldName()
        );
        assertEquals(
                LocalDate.of(2023, 12, 31),
                exception.getFieldValue()
        );

        verify(maintenanceRepository, never())
                .save(any());
    }


    @Test
    void addMaintenance_shouldRejectDateAfterVehicleEndDate() {

        // Arrange
        MaintenanceRequestDTO request =
                createValidRequest();

        request.setDate(
                LocalDate.of(2025, 1, 1)
        );

        Vehicle vehicle =
                createVehicle();

        vehicle.setEndDate(
                LocalDate.of(2024, 12, 31)
        );

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        // Act
        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> maintenanceService.addMaintenance(request)
                );

        // Assert
        assertEquals(
                "Maintenance date cannot occur after vehicle end date",
                exception.getMessage()
        );
        assertEquals(
                "date",
                exception.getFieldName()
        );
        assertEquals(
                LocalDate.of(2025, 1, 1),
                exception.getFieldValue()
        );

        verify(maintenanceRepository, never())
                .save(any());
    }


    // =========================================================
    // GET ALL
    // =========================================================

    @Test
    void getAllMaintenance_shouldReturnMaintenanceRecords() {

        // Arrange
        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord record =
                createMaintenanceRecord(vehicle);

        record.setId(1L);

        Pageable pageable =
                PageRequest.of(0, 10);

        Page<MaintenanceRecord> page =
                new PageImpl<>(
                        List.of(record),
                        pageable,
                        1
                );

        when(maintenanceRepository.findAll(pageable))
                .thenReturn(page);

        // Act
        Page<MaintenanceResponseDTO> result =
                maintenanceService.getAllMaintenance(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        MaintenanceResponseDTO dto =
                result.getContent().get(0);

        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getVehicleId());
        assertEquals(
                "Oil change",
                dto.getDescription()
        );
        assertEquals(
                LocalDate.of(2025, 1, 15),
                dto.getDate()
        );

        verify(maintenanceRepository)
                .findAll(pageable);
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    void getMaintenanceById_shouldReturnMaintenanceSuccessfully() {

        // Arrange
        Long maintenanceId = 1L;

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord record =
                createMaintenanceRecord(vehicle);

        record.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(record));

        // Act
        MaintenanceResponseDTO result =
                maintenanceService.getMaintenanceById(
                        maintenanceId
                );

        // Assert
        assertNotNull(result);
        assertEquals(maintenanceId, result.getId());
        assertEquals(1L, result.getVehicleId());
        assertEquals(
                "Oil change",
                result.getDescription()
        );
        assertEquals(
                LocalDate.of(2025, 1, 15),
                result.getDate()
        );
        assertEquals(
                75.00,
                result.getCost()
        );

        verify(maintenanceRepository)
                .findById(maintenanceId);
    }


    @Test
    void getMaintenanceById_shouldThrowWhenMaintenanceDoesNotExist() {

        // Arrange
        Long maintenanceId = 999L;

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.getMaintenanceById(
                        maintenanceId
                )
        );

        verify(maintenanceRepository)
                .findById(maintenanceId);
    }


    // =========================================================
    // GET BY VEHICLE
    // =========================================================

    @Test
    void getMaintenanceForVehicle_shouldReturnMaintenanceRecords() {

        // Arrange
        Long vehicleId = 1L;

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord record =
                createMaintenanceRecord(vehicle);

        record.setId(1L);

        Pageable pageable =
                PageRequest.of(0, 10);

        Page<MaintenanceRecord> page =
                new PageImpl<>(
                        List.of(record),
                        pageable,
                        1
                );

        when(
                maintenanceRepository.findByVehicle_Id(
                        vehicleId,
                        pageable
                )
        ).thenReturn(page);

        // Act
        Page<MaintenanceResponseDTO> result =
                maintenanceService.getMaintenanceForVehicle(
                        vehicleId,
                        pageable
                );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        MaintenanceResponseDTO dto =
                result.getContent().get(0);

        assertEquals(1L, dto.getId());
        assertEquals(vehicleId, dto.getVehicleId());
        assertEquals(
                "Oil change",
                dto.getDescription()
        );

        verify(
                maintenanceRepository
        ).findByVehicle_Id(
                vehicleId,
                pageable
        );
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    void updateMaintenance_shouldUpdateMaintenanceSuccessfully() {

        // Arrange
        Long maintenanceId = 1L;

        MaintenanceRequestDTO request =
                createValidRequest();

        request.setDescription("Brake service");
        request.setDate(
                LocalDate.of(2025, 2, 1)
        );
        request.setCost(
                250.00
        );

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord existingRecord =
                createMaintenanceRecord(vehicle);

        existingRecord.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(existingRecord));

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        when(maintenanceRepository.save(existingRecord))
                .thenReturn(existingRecord);

        // Act
        MaintenanceResponseDTO result =
                maintenanceService.updateMaintenance(
                        maintenanceId,
                        request
                );

        // Assert
        assertNotNull(result);
        assertEquals(maintenanceId, result.getId());
        assertEquals(1L, result.getVehicleId());
        assertEquals(
                "Brake service",
                result.getDescription()
        );
        assertEquals(
                LocalDate.of(2025, 2, 1),
                result.getDate()
        );
        assertEquals(
                250.00,
                result.getCost()
        );

        verify(maintenanceRepository)
                .findById(maintenanceId);

        verify(vehicleRepository)
                .findById(1L);

        verify(maintenanceRepository)
                .save(existingRecord);
    }


    @Test
    void updateMaintenance_shouldThrowWhenMaintenanceDoesNotExist() {

        // Arrange
        Long maintenanceId = 999L;

        MaintenanceRequestDTO request =
                createValidRequest();

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.updateMaintenance(
                        maintenanceId,
                        request
                )
        );

        verify(vehicleRepository, never())
                .findById(any());

        verify(maintenanceRepository, never())
                .save(any());
    }


    @Test
    void updateMaintenance_shouldThrowWhenVehicleDoesNotExist() {

        // Arrange
        Long maintenanceId = 1L;

        MaintenanceRequestDTO request =
                createValidRequest();

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord existingRecord =
                createMaintenanceRecord(vehicle);

        existingRecord.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(existingRecord));

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.updateMaintenance(
                        maintenanceId,
                        request
                )
        );

        verify(maintenanceRepository)
                .findById(maintenanceId);

        verify(vehicleRepository)
                .findById(1L);

        verify(maintenanceRepository, never())
                .save(any());
    }


    @Test
    void updateMaintenance_shouldRejectDateBeforeVehicleStartDate() {

        // Arrange
        Long maintenanceId = 1L;

        MaintenanceRequestDTO request =
                createValidRequest();

        request.setDate(
                LocalDate.of(2023, 12, 31)
        );

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord existingRecord =
                createMaintenanceRecord(vehicle);

        existingRecord.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(existingRecord));

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        // Act
        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> maintenanceService.updateMaintenance(
                                maintenanceId,
                                request
                        )
                );

        // Assert
        assertEquals(
                "Maintenance date cannot occur before vehicle start date",
                exception.getMessage()
        );
        assertEquals(
                "date",
                exception.getFieldName()
        );
        assertEquals(
                LocalDate.of(2023, 12, 31),
                exception.getFieldValue()
        );

        verify(maintenanceRepository, never())
                .save(any());
    }


    @Test
    void updateMaintenance_shouldRejectDateAfterVehicleEndDate() {

        // Arrange
        Long maintenanceId = 1L;

        MaintenanceRequestDTO request =
                createValidRequest();

        request.setDate(
                LocalDate.of(2025, 1, 1)
        );

        Vehicle vehicle =
                createVehicle();

        vehicle.setEndDate(
                LocalDate.of(2024, 12, 31)
        );

        MaintenanceRecord existingRecord =
                createMaintenanceRecord(vehicle);

        existingRecord.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(existingRecord));

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        // Act
        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> maintenanceService.updateMaintenance(
                                maintenanceId,
                                request
                        )
                );

        // Assert
        assertEquals(
                "Maintenance date cannot occur after vehicle end date",
                exception.getMessage()
        );
        assertEquals(
                "date",
                exception.getFieldName()
        );
        assertEquals(
                LocalDate.of(2025, 1, 1),
                exception.getFieldValue()
        );

        verify(maintenanceRepository, never())
                .save(any());
    }


    // =========================================================
    // DELETE
    // =========================================================

    @Test
    void deleteMaintenance_shouldDeleteMaintenanceSuccessfully() {

        // Arrange
        Long maintenanceId = 1L;

        Vehicle vehicle =
                createVehicle();

        MaintenanceRecord record =
                createMaintenanceRecord(vehicle);

        record.setId(maintenanceId);

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.of(record));

        // Act
        maintenanceService.deleteMaintenance(maintenanceId);

        // Assert
        verify(maintenanceRepository)
                .findById(maintenanceId);

        verify(maintenanceRepository)
                .delete(record);
    }


    @Test
    void deleteMaintenance_shouldThrowWhenMaintenanceDoesNotExist() {

        // Arrange
        Long maintenanceId = 999L;

        when(maintenanceRepository.findById(maintenanceId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.deleteMaintenance(
                        maintenanceId
                )
        );

        verify(maintenanceRepository)
                .findById(maintenanceId);

        verify(maintenanceRepository, never())
                .delete(any());
    }


    // =========================================================
    // TEST DATA HELPERS
    // =========================================================

    private MaintenanceRequestDTO createValidRequest() {

        MaintenanceRequestDTO request =
                new MaintenanceRequestDTO();

        request.setVehicleId(1L);
        request.setDescription("Oil change");
        request.setDate(
                LocalDate.of(2025, 1, 15)
        );
        request.setCost(
                75.00
        );

        return request;
    }


    private Vehicle createVehicle() {

        Vehicle vehicle =
                new Vehicle();

        vehicle.setId(1L);
        vehicle.setStartDate(
                LocalDate.of(2024, 1, 1)
        );
        vehicle.setEndDate(null);

        return vehicle;
    }


    private MaintenanceRecord createMaintenanceRecord(
            Vehicle vehicle
    ) {

        MaintenanceRecord record =
                new MaintenanceRecord();

        record.setVehicle(vehicle);
        record.setDescription("Oil change");
        record.setServiceDate(
                LocalDate.of(2025, 1, 15)
        );
        record.setCost(
                75.00
        );

        return record;
    }
}

