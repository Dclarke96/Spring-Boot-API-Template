package com.dylanclarke.springbootapitemplate.repository;

import com.dylanclarke.springbootapitemplate.model.MaintenanceRecord;
import com.dylanclarke.springbootapitemplate.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceRecord, Long> {

    // ----------------------------------------
    // GET BY VEHICLE ID
    // ----------------------------------------
    Page<MaintenanceRecord> findByVehicle_Id(
            Long vehicleId,
            Pageable pageable
    );

}