package com.dylanclarke.springbootapitemplate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dylanclarke.springbootapitemplate.dto.MaintenanceRequestDTO;
import com.dylanclarke.springbootapitemplate.dto.MaintenanceResponseDTO;
import com.dylanclarke.springbootapitemplate.exception.ResourceNotFoundException;
import com.dylanclarke.springbootapitemplate.exception.ValidationException;
import com.dylanclarke.springbootapitemplate.model.MaintenanceRecord;
import com.dylanclarke.springbootapitemplate.model.Vehicle;
import com.dylanclarke.springbootapitemplate.repository.MaintenanceRepository;
import com.dylanclarke.springbootapitemplate.repository.VehicleRepository;

@Service
public class MaintenanceService {

    private static final Logger log =
            LoggerFactory.getLogger(MaintenanceService.class);


    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;


    public MaintenanceService(
            MaintenanceRepository maintenanceRepository,
            VehicleRepository vehicleRepository
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }


    // ----------------------------------------------------
    // VEHICLE LOOKUP
    // ----------------------------------------------------
    private Vehicle getVehicle(Long vehicleId) {

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle",
                                "id",
                                vehicleId
                        ));
    }


    // ----------------------------------------------------
    // GET ALL
    // ----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<MaintenanceResponseDTO> getAllMaintenance(
            Pageable pageable
    ) {

        return maintenanceRepository
                .findAll(pageable)
                .map(this::mapToDTO);
    }


    // ----------------------------------------------------
    // GET BY ID
    // ----------------------------------------------------
    @Transactional(readOnly = true)
    public MaintenanceResponseDTO getMaintenanceById(Long id) {

        MaintenanceRecord record =
                maintenanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Maintenance record",
                                        "id",
                                        id
                                ));

        return mapToDTO(record);
    }


    // ----------------------------------------------------
    // GET BY VEHICLE
    // ----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<MaintenanceResponseDTO> getMaintenanceForVehicle(
            Long vehicleId,
            Pageable pageable
    ) {

        return maintenanceRepository
                .findByVehicle_Id(vehicleId, pageable)
                .map(this::mapToDTO);
    }


    // ----------------------------------------------------
    // CREATE
    // ----------------------------------------------------
    @Transactional
    public MaintenanceResponseDTO addMaintenance(
            MaintenanceRequestDTO request
    ) {

        Vehicle vehicle =
                getVehicle(request.getVehicleId());


        MaintenanceRecord record =
                mapToEntity(request);


        validateRecord(record, vehicle);


        record.setVehicle(vehicle);


        MaintenanceRecord saved =
                maintenanceRepository.save(record);


        log.info(
                "Maintenance created: maintenanceId={}, vehicleId={}",
                saved.getId(),
                vehicle.getId()
        );


        return mapToDTO(saved);
    }


    // ----------------------------------------------------
    // UPDATE
    // ----------------------------------------------------
    @Transactional
    public MaintenanceResponseDTO updateMaintenance(
            Long id,
            MaintenanceRequestDTO request
    ) {

        MaintenanceRecord existing =
                maintenanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Maintenance record",
                                        "id",
                                        id
                                ));


        Vehicle vehicle =
                getVehicle(request.getVehicleId());


        existing.setDescription(request.getDescription());
        existing.setServiceDate(request.getDate());
        existing.setCost(request.getCost());
        existing.setVehicle(vehicle);


        validateRecord(existing, vehicle);


        MaintenanceRecord saved =
                maintenanceRepository.save(existing);


        log.info(
                "Maintenance updated: maintenanceId={}, vehicleId={}",
                saved.getId(),
                vehicle.getId()
        );


        return mapToDTO(saved);
    }


    // ----------------------------------------------------
    // DELETE
    // ----------------------------------------------------
    @Transactional
    public void deleteMaintenance(Long id) {

        MaintenanceRecord record =
                maintenanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Maintenance record",
                                        "id",
                                        id
                                ));


        maintenanceRepository.delete(record);


        log.info(
                "Maintenance deleted: maintenanceId={}, vehicleId={}",
                record.getId(),
                record.getVehicle().getId()
        );
    }


    // ----------------------------------------------------
    // MAPPING
    // ----------------------------------------------------
    private MaintenanceResponseDTO mapToDTO(
            MaintenanceRecord record
    ) {

        MaintenanceResponseDTO dto =
                new MaintenanceResponseDTO();

        dto.setId(record.getId());
        dto.setVehicleId(record.getVehicle().getId());
        dto.setDescription(record.getDescription());
        dto.setDate(record.getServiceDate());
        dto.setCost(record.getCost());

        return dto;
    }


    private MaintenanceRecord mapToEntity(
            MaintenanceRequestDTO request
    ) {

        MaintenanceRecord record =
                new MaintenanceRecord();

        record.setDescription(request.getDescription());
        record.setServiceDate(request.getDate());
        record.setCost(request.getCost());

        return record;
    }


    // ----------------------------------------------------
    // BUSINESS VALIDATION
    // ----------------------------------------------------
    private void validateRecord(
            MaintenanceRecord record,
            Vehicle vehicle
    ) {

        LocalDate serviceDate = record.getServiceDate();


        if (serviceDate.isBefore(vehicle.getStartDate())) {

            throw new ValidationException(
                    "Maintenance date cannot occur before vehicle start date",
                    "date",
                    serviceDate
            );
        }


        if (vehicle.getEndDate() != null &&
                serviceDate.isAfter(vehicle.getEndDate())) {

            throw new ValidationException(
                    "Maintenance date cannot occur after vehicle end date",
                    "date",
                    serviceDate
            );
        }
    }
}