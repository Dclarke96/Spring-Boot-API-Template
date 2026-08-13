package com.dylanclarke.springbootapitemplate.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Vehicle data returned by the API."
)
public class VehicleResponseDTO {

    @Schema(
            description = "Unique identifier of the vehicle.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "User-defined title for the vehicle.",
            example = "Company Truck 01"
    )
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
    private String make;

    @Schema(
            description = "Vehicle model.",
            example = "F-150"
    )
    private String model;

    @Schema(
            description = "Manufacturing year of the vehicle.",
            example = "2022"
    )
    private Integer vehicleYear;

    @Schema(
            description = "Current location of the vehicle.",
            example = "Washington, DC"
    )
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

    public VehicleResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
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
