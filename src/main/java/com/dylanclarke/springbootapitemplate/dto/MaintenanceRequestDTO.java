package com.dylanclarke.springbootapitemplate.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(
        description = "Request data used to create or update a maintenance record."
)
public class MaintenanceRequestDTO {

    @Schema(
            description = "Unique identifier of the vehicle associated with the maintenance record.",
            example = "1"
    )
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @Schema(
            description = "Description of the maintenance performed or required.",
            example = "Oil and filter change"
    )
    @NotBlank(message = "Description is required")
    @Size(min = 3, message = "Description must be at least 3 characters")
    private String description;

    @Schema(
            description = "Date the maintenance was performed or is scheduled.",
            example = "2026-07-15"
    )
    @NotNull(message = "Service date is required")
    private LocalDate date;

    @Schema(
            description = "Cost of the maintenance service.",
            example = "149.99",
            minimum = "0"
    )
    @NotNull(message = "Cost is required")
    @PositiveOrZero(message = "Cost must be zero or positive")
    private Double cost;

    // Getters and Setters

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
