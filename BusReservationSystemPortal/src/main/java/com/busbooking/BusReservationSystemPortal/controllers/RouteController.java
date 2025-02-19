package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.models.Route;
import com.busbooking.BusReservationSystemPortal.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PostMapping("/route/admin")
    public ResponseEntity<Route> addRoute(@Valid @RequestBody Route route, @RequestParam(required = false) String key) throws RouteException, AdminException {

        Route newRoute= routeService.addRoute(route,key);

        return new ResponseEntity<Route>(newRoute, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/route/admin/{routeId}")
    public ResponseEntity<Route> DeleteRoute(@PathVariable("routeId") Integer routeId,@RequestParam(required = false) String key) throws RouteException, AdminException{

        Route route = routeService.deleteRoute(routeId,key);

        return new ResponseEntity<Route>(route,HttpStatus.GONE);
    }

    @PutMapping("/route/admin")
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route route,@RequestParam(required = false) String key) throws RouteException, AdminException{

        Route newRoute= routeService.updateRoute(route,key);

        return new ResponseEntity<Route>(newRoute,HttpStatus.OK);
    }

    @GetMapping("/routes")
    public ResponseEntity<List<Route>> getAllRoutes() throws RouteException{

        List<Route> routes = routeService.viewAllRoute();

        return new ResponseEntity<List<Route>>(routes,HttpStatus.OK);
    }


    @GetMapping("/route/{routeId}")
    public ResponseEntity<Route> getRouteById(@PathVariable("routeId") Integer routeId) throws RouteException{

        Route route = routeService.viewRoute(routeId);

        return new ResponseEntity<Route>(route,HttpStatus.OK);
    }
}
