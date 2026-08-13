package com.dylanclarke.springbootapitemplate.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Maintenance record data returned by the API."
)
public class MaintenanceResponseDTO {

    @Schema(
            description = "Unique identifier of the maintenance record.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Unique identifier of the vehicle associated with the maintenance record.",
            example = "1"
    )
    private Long vehicleId;

    @Schema(
            description = "Description of the maintenance performed or required.",
            example = "Oil and filter change"
    )
    private String description;

    @Schema(
            description = "Date the maintenance was performed or is scheduled.",
            example = "2026-07-15"
    )
    private LocalDate date;

    @Schema(
            description = "Cost of the maintenance service.",
            example = "149.99",
            minimum = "0"
    )
    private Double cost;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
