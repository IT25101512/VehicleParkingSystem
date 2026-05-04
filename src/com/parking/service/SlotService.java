package com.parking.service;

import com.parking.model.ParkingSlot;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.util.*;

public class SlotService {
    private static final String FILE = "slots.txt";

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    public List<ParkingSlot> getAllSlots(ServletContext ctx) {
        List<ParkingSlot> slots = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            ParkingSlot s = ParkingSlot.fromString(line);
            if (s != null) slots.add(s);
        }
        return slots;
    }

    public ParkingSlot getSlotById(ServletContext ctx, String slotId) {
        for (ParkingSlot s : getAllSlots(ctx)) {
            if (s.getSlotId().equals(slotId)) return s;
        }
        return null;
    }

    public List<ParkingSlot> getAvailableSlots(ServletContext ctx, String type) {
        List<ParkingSlot> result = new ArrayList<>();
        for (ParkingSlot s : getAllSlots(ctx)) {
            if (s.getStatus().equals("AVAILABLE") &&
                    (type == null || type.isEmpty() || s.getType().equalsIgnoreCase(type))) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean addSlot(ServletContext ctx, ParkingSlot slot) {
        slot.setSlotId(FileHandler.generateId("S"));
        FileHandler.appendLine(path(ctx), slot.toString());
        return true;
    }

    public boolean updateSlot(ServletContext ctx, ParkingSlot updated) {
        List<ParkingSlot> slots = getAllSlots(ctx);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (ParkingSlot s : slots) {
            if (s.getSlotId().equals(updated.getSlotId())) {
                lines.add(updated.toString());
                found = true;
            } else lines.add(s.toString());
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }

    public boolean setSlotStatus(ServletContext ctx, String slotId, String status) {
        ParkingSlot slot = getSlotById(ctx, slotId);
        if (slot == null) return false;
        slot.setStatus(status);
        return updateSlot(ctx, slot);
    }

    public boolean deleteSlot(ServletContext ctx, String slotId) {
        List<ParkingSlot> slots = getAllSlots(ctx);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (ParkingSlot s : slots) {
            if (!s.getSlotId().equals(slotId)) lines.add(s.toString());
            else found = true;
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }

    public long countAvailable(ServletContext ctx) {
        return getAllSlots(ctx).stream().filter(s -> s.getStatus().equals("AVAILABLE")).count();
    }

    public long countOccupied(ServletContext ctx) {
        return getAllSlots(ctx).stream().filter(s -> s.getStatus().equals("OCCUPIED")).count();
    }

    public void getSlotService(ServletContext servletContext, String slotId, String available) {
    }
}
