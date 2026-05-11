package com.parking.model;

/**
 * Vehicle — extends BaseVehicle (Inheritance).
 * Inherits vehicleId, plateNumber, ownerUserId from BaseVehicle.
 * Adds type, model, color fields specific to a vehicle.
 */
public class Vehicle extends BaseVehicle {

    private String type; // CAR, BIKE, TRUCK
    private String model;
    private String color;

    // No-arg constructor — calls parent no-arg constructor
    public Vehicle() {
        super();
    }

    // Full constructor — calls parent constructor with common fields
    public Vehicle(String vehicleId, String plateNumber, String type,
                   String ownerUserId, String model, String color) {
        super(vehicleId, plateNumber, ownerUserId); // sends to BaseVehicle
        this.type = type;
        this.model = model;
        this.color = color;
    }

    /**
     * Overrides BaseVehicle.calculateFee() — Method Overriding + Polymorphism.
     * Different rates based on vehicle type.
     */
    @Override
    public double calculateFee(int hours) {
        if (type == null) return hours * 10;
        switch (type.toUpperCase()) {
            case "BIKE":  return hours * 10;
            case "CAR":   return hours * 20;
            case "TRUCK": return hours * 40;
            default:      return hours * 10;
        }
    }

    // Getters & Setters for Vehicle-specific fields
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    /**
     * Converts Vehicle object to CSV string for file storage.
     * Uses getters from BaseVehicle for inherited fields.
     */
    @Override
    public String toString() {
        return getVehicleId() + "," + getPlateNumber() + "," +
                type + "," + getOwnerUserId() + "," + model + "," + color;
    }

    /**
     * Creates a Vehicle object from a CSV line read from file.
     */
    public static Vehicle fromString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 6) return null;
        return new Vehicle(parts[0], parts[1], parts[2],
                parts[3], parts[4], parts[5]);
    }
}