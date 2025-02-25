package com.busbooking.BusReservationSystemPortal.controllers;

import com.busbooking.BusReservationSystemPortal.DTO.ReservationDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.ReservationException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Reservation;
import com.busbooking.BusReservationSystemPortal.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Reservation Management", description = "Handles reservation-related operations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation( summary = "Add a reservation", description = "Allows a user to create a new reservation" )
    @PostMapping("/user")
    public ResponseEntity<Reservation> addReservation(
            @Valid @RequestBody ReservationDTO reservationDTO,
            @RequestParam(required = false) String key)
            throws ReservationException, BusException, UserException {

        Reservation savedReservation = reservationService.addReservation(reservationDTO, key);
        return new ResponseEntity<>(savedReservation, HttpStatus.ACCEPTED);
    }

    @Operation( summary = "Delete a reservation", description = "Allows a user to delete an existing reservation by ID")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Reservation> deleteReservation(
            @PathVariable("id") Integer reservationId,
            @RequestParam(required = false) String key)
            throws ReservationException, BusException, UserException {

        Reservation deletedReservation = reservationService.deleteReservation(reservationId, key);
        return new ResponseEntity<>(deletedReservation, HttpStatus.OK);
    }

    @Operation(summary = "View a reservation (Admin)", description = "Allows an admin to view a reservation by ID" )
    @GetMapping("/admin/{id}")
    public ResponseEntity<Reservation> viewReservation(
            @PathVariable("id") Integer reservationId,
            @RequestParam(required = false) String key)
            throws ReservationException, AdminException {

        Reservation foundReservation = reservationService.viewReservation(reservationId, key);
        return new ResponseEntity<>(foundReservation, HttpStatus.OK);
    }

    @Operation(summary = "View all reservations (Admin)", description = "Allows an admin to view all reservations")
    @GetMapping("/admin")
    public ResponseEntity<List<Reservation>> viewAllReservation(
            @RequestParam(required = false) String key)
            throws ReservationException, UserException {

        List<Reservation> reservationList = reservationService.viewAllReservation(key);
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }

    @Operation(summary = "View reservations by user", description = "Allows a user to view their reservations")
    @GetMapping("/user")
    public ResponseEntity<List<Reservation>> viewReservationByUser(
            @RequestParam(required = false) String key)
            throws ReservationException, UserException {

        List<Reservation> reservationList = reservationService.viewReservationByUser(key);
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }
}
