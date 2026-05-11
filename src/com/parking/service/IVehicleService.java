package com.parking.service;

import com.parking.model.Vehicle;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * Interface for Vehicle Service — defines the contract.
 * VehicleService implements this interface.
 */
public interface IVehicleService {
    List<Vehicle> getAllVehicles(ServletContext ctx);
    Vehicle getVehicleById(ServletContext ctx, String vehicleId);
    Vehicle getVehicleByPlate(ServletContext ctx, String plate);
    List<Vehicle> getVehiclesByUser(ServletContext ctx, String userId);
    boolean addVehicle(ServletContext ctx, Vehicle vehicle);
    boolean updateVehicle(ServletContext ctx, Vehicle updated);
    boolean deleteVehicle(ServletContext ctx, String vehicleId);
}