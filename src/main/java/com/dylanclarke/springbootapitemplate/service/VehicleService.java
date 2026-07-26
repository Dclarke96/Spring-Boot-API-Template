package com.dylanclarke.springbootapitemplate.service;

import com.dylanclarke.springbootapitemplate.dto.VehicleRequestDTO;
import com.dylanclarke.springbootapitemplate.dto.VehicleResponseDTO;
import com.dylanclarke.springbootapitemplate.model.Vehicle;
import com.dylanclarke.springbootapitemplate.exception.ResourceNotFoundException;
import com.dylanclarke.springbootapitemplate.exception.ValidationException;
import com.dylanclarke.springbootapitemplate.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class VehicleService {

    private static final Logger log =
            LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository repository;


    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }


    // ----------------------------------------
    // GET ALL (PAGINATED)
    // ----------------------------------------
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehicles(Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::toDto);
    }


    // ----------------------------------------
    // GET BY ID
    // ----------------------------------------
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicleById(Long id) {

        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle",
                                "id",
                                id
                        ));

        return toDto(vehicle);
    }


    // ----------------------------------------
    // SEARCH (PAGINATED)
    // ----------------------------------------
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> searchVehicles(
            String query,
            Pageable pageable
    ) {

        return repository.searchVehicles(query, pageable)
                .map(this::toDto);
    }


    // ----------------------------------------
    // CREATE
    // ----------------------------------------
    @Transactional
    public VehicleResponseDTO addVehicle(VehicleRequestDTO dto) {

        Vehicle entity = toEntity(dto);

        validateVehicle(entity);

        Vehicle saved = repository.save(entity);

        log.info(
                "Vehicle created: vehicleId={}",
                saved.getId()
        );

        return toDto(saved);
    }


    // ----------------------------------------
    // UPDATE
    // ----------------------------------------
    @Transactional
    public VehicleResponseDTO updateVehicle(
            Long id,
            VehicleRequestDTO dto
    ) {

        Vehicle existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle",
                                "id",
                                id
                        ));


        updateEntityFromDto(existing, dto);

        validateVehicle(existing);


        Vehicle saved = repository.save(existing);


        log.info(
                "Vehicle updated: vehicleId={}",
                saved.getId()
        );


        return toDto(saved);
    }


    // ----------------------------------------
    // DELETE
    // ----------------------------------------
    @Transactional
    public void deleteVehicle(Long id) {

        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle",
                                "id",
                                id
                        ));


        repository.delete(vehicle);


        log.info(
                "Vehicle deleted: vehicleId={}",
                id
        );
    }


    // ----------------------------------------
    // DTO MAPPING
    // ----------------------------------------
    private VehicleResponseDTO toDto(Vehicle v) {

        VehicleResponseDTO dto = new VehicleResponseDTO();

        dto.setId(v.getId());
        dto.setTitle(v.getTitle());
        dto.setVin(v.getVin());
        dto.setLicensePlate(v.getLicensePlate());
        dto.setMake(v.getMake());
        dto.setModel(v.getModel());
        dto.setVehicleYear(v.getVehicleYear());
        dto.setLocation(v.getLocation());
        dto.setMaintenanceAlertsEnabled(
                v.isMaintenanceAlertsEnabled()
        );
        dto.setStartDate(v.getStartDate());
        dto.setEndDate(v.getEndDate());

        return dto;
    }


    private Vehicle toEntity(VehicleRequestDTO dto) {

        Vehicle v = new Vehicle();

        v.setTitle(dto.getTitle());
        v.setVin(dto.getVin());
        v.setLicensePlate(dto.getLicensePlate());
        v.setMake(dto.getMake());
        v.setModel(dto.getModel());
        v.setVehicleYear(dto.getYear());
        v.setLocation(dto.getLocation());

        v.setMaintenanceAlertsEnabled(
                dto.getMaintenanceAlertsEnabled() != null
                        ? dto.getMaintenanceAlertsEnabled()
                        : false
        );

        v.setStartDate(dto.getStartDate());
        v.setEndDate(dto.getEndDate());

        return v;
    }


    private void updateEntityFromDto(
            Vehicle vehicle,
            VehicleRequestDTO dto
    ) {

        vehicle.setTitle(dto.getTitle());
        vehicle.setVin(dto.getVin());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setVehicleYear(dto.getYear());
        vehicle.setLocation(dto.getLocation());

        vehicle.setMaintenanceAlertsEnabled(
                dto.getMaintenanceAlertsEnabled() != null
                        ? dto.getMaintenanceAlertsEnabled()
                        : false
        );

        vehicle.setStartDate(dto.getStartDate());
        vehicle.setEndDate(dto.getEndDate());
    }


    // ----------------------------------------
    // BUSINESS VALIDATION
    // ----------------------------------------
    private void validateVehicle(Vehicle vehicle) {

        Integer year = vehicle.getVehicleYear();


        if (year == null) {

            throw new ValidationException(
                    "Vehicle year is required",
                    "vehicleYear",
                    null
            );
        }


        int currentYear = LocalDate.now().getYear();


        if (year < 1900 || year > currentYear) {

            throw new ValidationException(
                    "Year must be between 1900 and " + currentYear,
                    "vehicleYear",
                    year
            );
        }


        LocalDate start = vehicle.getStartDate();
        LocalDate end = vehicle.getEndDate();


        if (start == null) {

            throw new ValidationException(
                    "Start date is required",
                    "startDate",
                    null
            );
        }


        if (end != null && end.isBefore(start)) {

            throw new ValidationException(
                    "End date cannot be before start date",
                    "endDate",
                    end
            );
        }
    }
}