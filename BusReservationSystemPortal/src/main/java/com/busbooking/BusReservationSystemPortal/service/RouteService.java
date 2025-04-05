package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Route;

import java.util.List;

public interface RouteService {

    public Route addRoute(Route route) throws RouteException, AdminException, UserException;

    public Route updateRoute(Route route) throws RouteException, AdminException, UserException;

    public Route deleteRoute(int routeId) throws RouteException, AdminException, UserException;

    public Route viewRoute(int routeId) throws RouteException;

    public List<Route> viewAllRoute() throws RouteException;
}
