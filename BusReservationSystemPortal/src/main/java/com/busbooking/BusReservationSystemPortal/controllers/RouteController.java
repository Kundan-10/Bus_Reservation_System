package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Route;
import com.busbooking.BusReservationSystemPortal.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
@Tag(name = "Route Controller", description = "Handles Route operations")
public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "Add Route", description = "Adds a new bus route (Admin Only)")
    @PostMapping("/admin")
    public ResponseEntity<Route> addRoute(@Valid @RequestBody Route route)
            throws RouteException, AdminException, UserException {
        Route newRoute = routeService.addRoute(route);
        return new ResponseEntity<>(newRoute, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete Route", description = "Deletes a bus route by ID (Admin Only)")
    @DeleteMapping("/admin/{routeId}")
    public ResponseEntity<Route> deleteRoute(@PathVariable("routeId") Integer routeId)
            throws RouteException, AdminException, UserException {
        Route route = routeService.deleteRoute(routeId);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @Operation(summary = "Update Route", description = "Updates an existing bus route (Admin Only)")
    @PutMapping("/admin")
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route route)
            throws RouteException, AdminException, UserException {
        Route newRoute = routeService.updateRoute(route);
        return new ResponseEntity<>(newRoute, HttpStatus.OK);
    }

    @Operation(summary = "View All Routes", description = "Retrieves all available routes")
    @GetMapping("/all")
    public ResponseEntity<List<Route>> getAllRoutes() throws RouteException {
        List<Route> routes = routeService.viewAllRoute();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @Operation(summary = "View Route by ID", description = "Retrieves a specific route by ID")
    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getRouteById(@PathVariable("routeId") Integer routeId) throws RouteException {
        Route route = routeService.viewRoute(routeId);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }
}
