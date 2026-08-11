package com.dylanclarke.springbootapitemplate.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        description = "Request data used to create or update a vehicle."
)
public class VehicleRequestDTO {

    @Schema(
            description = "User-defined title for the vehicle.",
            example = "Company Truck 01"
    )
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Schema(
            description = "Vehicle identification number.",
            example = "1HGCM82633A123456"
    )
    private String vin;

    @Schema(
            description = "Vehicle license plate number.",
            example = "ABC-1234"
    )
    private String licensePlate;

    @Schema(
            description = "Vehicle manufacturer.",
            example = "Ford"
    )
    @NotBlank(message = "Make cannot be blank")
    private String make;

    @Schema(
            description = "Vehicle model.",
            example = "F-150"
    )
    @NotBlank(message = "Model cannot be blank")
    private String model;

    @Schema(
            description = "Manufacturing year of the vehicle.",
            example = "2022",
            minimum = "1886"
    )
    @NotNull(message = "Year cannot be null")
    @JsonProperty("vehicleYear")
    @Min(value = 1886, message = "Year must be 1886 or later")
    private Integer year;

    @Schema(
            description = "Current location of the vehicle.",
            example = "Washington, DC"
    )
    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Schema(
            description = "Whether maintenance alerts are enabled for the vehicle.",
            example = "true"
    )
    private Boolean maintenanceAlertsEnabled;

    @Schema(
            description = "Start date associated with the vehicle.",
            example = "2026-01-01"
    )
    private LocalDate startDate;

    @Schema(
            description = "End date associated with the vehicle.",
            example = "2026-12-31"
    )
    private LocalDate endDate;

    public VehicleRequestDTO() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getMaintenanceAlertsEnabled() {
        return maintenanceAlertsEnabled;
    }

    public void setMaintenanceAlertsEnabled(Boolean maintenanceAlertsEnabled) {
        this.maintenanceAlertsEnabled = maintenanceAlertsEnabled;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
