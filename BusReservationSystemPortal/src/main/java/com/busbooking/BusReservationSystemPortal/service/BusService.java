package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.models.Bus;

import java.util.List;

public interface BusService {


        public Bus addBus(Bus bus,String key) throws BusException, AdminException;

        public Bus updateBus(Bus bus,String key) throws BusException, AdminException;

        public Bus deleteBus(Integer busId,String key) throws BusException, AdminException;

        public Bus viewBus(Integer busId) throws BusException;

        public List<Bus> viewBusByType(String busType) throws BusException;

        public List<Bus> viewAllBuses() throws BusException;

}
