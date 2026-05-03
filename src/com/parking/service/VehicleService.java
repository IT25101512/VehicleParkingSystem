package com.parking.service;

import com.parking.model.Vehicle;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * VehicleService — implements IVehicleService (Interface).
 * Contains all CRUD operations for vehicles.
 * All logic stays exactly the same as your original code.
 */
public class VehicleService implements IVehicleService {

    private static final String FILE = "vehicles.txt";

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    // READ — Get all vehicles from file
    @Override
    public List<Vehicle> getAllVehicles(ServletContext ctx) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            Vehicle v = Vehicle.fromString(line);
            if (v != null) vehicles.add(v);
        }
        return vehicles;
    }

    // READ — Get single vehicle by ID
    @Override
    public Vehicle getVehicleById(ServletContext ctx, String vehicleId) {
        for (Vehicle v : getAllVehicles(ctx)) {
            if (v.getVehicleId().equals(vehicleId)) return v;
        }
        return null;
    }

    // READ — Get vehicle by plate number
    @Override
    public Vehicle getVehicleByPlate(ServletContext ctx, String plate) {
        for (Vehicle v : getAllVehicles(ctx)) {
            if (v.getPlateNumber().equalsIgnoreCase(plate)) return v;
        }
        return null;
    }

    // READ — Get all vehicles belonging to a user
    @Override
    public List<Vehicle> getVehiclesByUser(ServletContext ctx, String userId) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : getAllVehicles(ctx)) {
            if (v.getOwnerUserId().equals(userId)) result.add(v);
        }
        return result;
    }

    // CREATE — Add new vehicle (checks duplicate plate first)
    @Override
    public boolean addVehicle(ServletContext ctx, Vehicle vehicle) {
        if (getVehicleByPlate(ctx, vehicle.getPlateNumber()) != null) return false;
        vehicle.setVehicleId(FileHandler.generateId("V"));
        FileHandler.appendLine(path(ctx), vehicle.toString());
        return true;
    }

    // UPDATE — Update existing vehicle record
    @Override
    public boolean updateVehicle(ServletContext ctx, Vehicle updated) {
        List<Vehicle> vehicles = getAllVehicles(ctx);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equals(updated.getVehicleId())) {
                lines.add(updated.toString());
                found = true;
            } else {
                lines.add(v.toString());
            }
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }

    // DELETE — Remove vehicle by ID
    @Override
    public boolean deleteVehicle(ServletContext ctx, String vehicleId) {
        List<Vehicle> vehicles = getAllVehicles(ctx);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (!v.getVehicleId().equals(vehicleId)) {
                lines.add(v.toString());
            } else {
                found = true;
            }
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }
}