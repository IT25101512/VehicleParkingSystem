package com.parking.controller;

import com.parking.model.ParkingSlot;
import com.parking.service.SlotService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/slot/*")
public class SlotServlet extends HttpServlet {
    private final SlotService slotService = new SlotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if (action == null) action = "/view";

        switch (action) {
            case "/view":
                List<ParkingSlot> slots = slotService.getAllSlots(getServletContext());
                req.setAttribute("slots", slots);
                req.setAttribute("available", slotService.countAvailable(getServletContext()));
                req.setAttribute("occupied", slotService.countOccupied(getServletContext()));
                req.getRequestDispatcher("/pages/slot/view.jsp").forward(req, resp);
                break;
            case "/allocate":
                String type = req.getParameter("type");
                List<ParkingSlot> avail = slotService.getAvailableSlots(getServletContext(), type);
                req.setAttribute("availableSlots", avail);
                req.getRequestDispatcher("/pages/slot/allocate.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/slot/view");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/add".equals(action)) {
            ParkingSlot slot = new ParkingSlot(null,
                    req.getParameter("slotNumber"),
                    req.getParameter("type"),
                    "AVAILABLE",
                    req.getParameter("floor"));
            slotService.addSlot(getServletContext(), slot);
            resp.sendRedirect(req.getContextPath() + "/slot/view");
        }
    }
}
