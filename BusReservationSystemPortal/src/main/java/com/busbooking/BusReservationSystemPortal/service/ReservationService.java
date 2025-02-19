package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.ReservationDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.ReservationException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Reservation;

import java.util.List;

public interface ReservationService {

    public Reservation addReservation(ReservationDTO reservationDTO, String key) throws ReservationException, BusException,UserException ;

    public Reservation deleteReservation(Integer reservationId, String key) throws ReservationException, BusException, UserException;

    public Reservation viewReservation(Integer reservationId,String key) throws ReservationException, AdminException;

    public List<Reservation> viewAllReservation(String key)throws ReservationException;

    public List<Reservation> viewReservationByUser(String key) throws ReservationException, UserException;
}
