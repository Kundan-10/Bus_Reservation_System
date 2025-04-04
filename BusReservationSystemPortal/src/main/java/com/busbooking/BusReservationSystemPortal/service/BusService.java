package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;

import java.util.List;

public interface BusService {


        public Bus addBus(Bus bus) throws BusException, AdminException, UserException;

        public Bus updateBus(Bus bus) throws BusException, AdminException, UserException;

        public Bus deleteBus(Integer busId) throws BusException, AdminException, UserException;

        public Bus viewBus(Integer busId) throws BusException;

        public List<Bus> viewBusByType(String busType) throws BusException;

        public List<Bus> viewAllBuses() throws BusException;

}
