package com.parking.model;

/**
 * BaseVehicle — Parent class.
 * Holds common fields shared by all vehicle types.
 * Vehicle.java extends this class (Inheritance).
 */
public class BaseVehicle {

    private String vehicleId;
    private String plateNumber;
    private String ownerUserId;

    // No-arg constructor
    public BaseVehicle() {}

    // Parameterized constructor
    public BaseVehicle(String vehicleId, String plateNumber, String ownerUserId) {
        this.vehicleId = vehicleId;
        this.plateNumber = plateNumber;
        this.ownerUserId = ownerUserId;
    }

    // Base fee calculation — overridden in Vehicle based on type
    public double calculateFee(int hours) {
        return hours * 10; // default base rate
    }

    // Getters & Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(String ownerUserId) { this.ownerUserId = ownerUserId; }
}