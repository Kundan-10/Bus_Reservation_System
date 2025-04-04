package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bus")
@Tag(name = "Bus Management", description = "Endpoints for managing Buses")
public class BusController {

    private final BusService busService;

    @PostMapping("/bus")
    @Operation(summary = "Add Bus", description = "Save a new bus in the system")
    public ResponseEntity<Bus> addBus(@Valid @RequestBody Bus bus) throws AdminException, UserException, BusException {
        Bus bus2 = busService.addBus(bus);
        return new ResponseEntity<>(bus2, HttpStatus.CREATED);
    }


    @PutMapping("/updateBus")
    @Operation(summary = "Update bus", description = "Update an existing bus's details")
    public ResponseEntity<Bus> updateBus(@Valid @RequestBody Bus bus) throws AdminException, UserException, BusException {
        Bus updateBus = busService.updateBus(bus);
        return new ResponseEntity<>(updateBus, HttpStatus.OK);
    }

    @DeleteMapping("/{busId}")
    @Operation(summary = "Delete Bus", description = "Delete a Bus by ID (Admin access required)")
    public ResponseEntity<Bus> deleteBus(@Valid @PathVariable("busId") Integer busId) throws AdminException, UserException, BusException {
        Bus updateBus = busService.deleteBus(busId);
        return new ResponseEntity<>(updateBus, HttpStatus.OK);
    }

    @GetMapping("/{busId}")
    @Operation(summary = "View Bus by ID", description = "View a Bus's details by ID (Admin access required)")
    public ResponseEntity<Bus> viewBus(@PathVariable("busId") Integer busId) throws BusException {
        Bus viewBus = busService.viewBus(busId);
        return new ResponseEntity<Bus>(viewBus, HttpStatus.OK);
    }

    @GetMapping("/bus")
    @Operation(summary = "View All Buses", description = "View a list of all Buses (Admin access required)")
    public ResponseEntity<List<Bus>> viewBuses() throws BusException {
        List<Bus> buses = busService.viewAllBuses();
        return new ResponseEntity<List<Bus>>(buses, HttpStatus.OK);
    }

}
