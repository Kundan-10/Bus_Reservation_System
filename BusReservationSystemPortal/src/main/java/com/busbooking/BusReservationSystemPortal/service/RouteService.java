package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.models.Route;

import java.util.List;

public interface RouteService {

    public Route addRoute(Route route, String key) throws RouteException, AdminException;

    public Route updateRoute(Route route,String key) throws RouteException, AdminException;

    public Route deleteRoute(int routeId,String key) throws RouteException, AdminException;

    public Route viewRoute(int routeId) throws RouteException;

    public List<Route> viewAllRoute() throws RouteException;
}
