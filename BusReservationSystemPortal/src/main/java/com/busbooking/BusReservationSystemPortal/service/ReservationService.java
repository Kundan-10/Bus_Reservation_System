package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.ReservationDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.ReservationException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Reservation;

import java.util.List;

public interface ReservationService {

    public Reservation addReservation(ReservationDTO reservationDTO) throws ReservationException, BusException,UserException ;

    public Reservation deleteReservation(Integer reservationId) throws ReservationException, BusException, UserException;

    public Reservation viewReservation(Integer reservationId) throws ReservationException, AdminException, UserException;

    public List<Reservation> viewAllReservation() throws ReservationException, UserException;

    public List<Reservation> viewReservationByUser() throws ReservationException, UserException;
}
