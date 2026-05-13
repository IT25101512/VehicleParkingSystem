package com.parking.model;

public class ParkingSlot {
    private String slotId;
    private String slotNumber;
    private String type; // CAR, BIKE, TRUCK
    private String status; // AVAILABLE, OCCUPIED
    private String floor;

    public ParkingSlot() {}

    public ParkingSlot(String slotId, String slotNumber, String type, String status, String floor) {
        this.slotId = slotId;
        this.slotNumber = slotNumber;
        this.type = type;
        this.status = status;
        this.floor = floor;
    }

    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    @Override
    public String toString() {
        return slotId + "," + slotNumber + "," + type + "," + status + "," + floor;
    }

    public static ParkingSlot fromString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 5) return null;
        return new ParkingSlot(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}