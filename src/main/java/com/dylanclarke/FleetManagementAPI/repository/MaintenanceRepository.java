package com.dylanclarke.FleetManagementAPI.repository;

import com.dylanclarke.FleetManagementAPI.model.MaintenanceRecord;
import com.dylanclarke.FleetManagementAPI.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceRecord, Long> {


    // ----------------------------------------
    // GET ALL BY VEHICLE
    // ----------------------------------------
    Page<MaintenanceRecord> findByVehicle(
            Vehicle vehicle,
            Pageable pageable
    );


    // ----------------------------------------
    // GET BY VEHICLE ID
    // ----------------------------------------
    Page<MaintenanceRecord> findByVehicle_Id(
            Long vehicleId,
            Pageable pageable
    );


    // ----------------------------------------
    // GET BY ID + VEHICLE
    // ----------------------------------------
    Optional<MaintenanceRecord> findByIdAndVehicle(
            Long id,
            Vehicle vehicle
    );

}