package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.models.Route;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminDao;
import com.busbooking.BusReservationSystemPortal.repositoty.AdminSessionDao;
import com.busbooking.BusReservationSystemPortal.repositoty.RouteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final AdminDao adminDao;
    private final RouteDao routeDao;
    private final AdminSessionDao adminSessionDao;

    @Override
    public Route addRoute(Route route, String key) throws RouteException, AdminException {
        validateAdminSession(key);
        Optional<Route> existingRoute = Optional.ofNullable(routeDao.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo()));
        if (existingRoute.isPresent()) {
            throw new RouteException("Route from " + route.getRouteFrom() + " to " + route.getRouteTo() + " already exists!");
        }
        route.setBusList(new ArrayList<>());
        return routeDao.save(route);
    }

    @Override
    public Route updateRoute(Route route, String key) throws RouteException, AdminException {
        validateAdminSession(key);

        Optional<Route> existedRoute = routeDao.findById(route.getRouteId());
        if(existedRoute.isPresent()) {

            Route presentRoute = existedRoute.get();
            List<Bus> busList = presentRoute.getBusList();

            if(!busList.isEmpty()) throw new RouteException("Cannot update running route! Buses are already scheduled in the route.");
            return routeDao.save(route);
        }
        else
            throw new RouteException("Route doesn't exist with routeId : "+ route.getRouteId());
    }

    @Override
    public Route deleteRoute(int routeId, String key) throws RouteException, AdminException {
        validateAdminSession(key);
        Route existingRoute = routeDao.findById(routeId)
                .orElseThrow(() -> new RouteException("There is no route present of routeId :" + routeId));
        routeDao.delete(existingRoute);
        return existingRoute;
    }

    @Override
    public Route viewRoute(int routeId) throws RouteException {
        Route existingRoute = routeDao.findById(routeId)
                .orElseThrow(() -> new RouteException("There is no route available" + routeId));

        return existingRoute;

    }

    @Override
    public List<Route> viewAllRoute() throws RouteException {
        List<Route> routes = routeDao.findAll();
        return Optional.of(routes)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new RouteException("There is no route available"));
    }

    private void validateAdminSession(String key) throws AdminException {
        if (adminSessionDao.findByUuid(key) == null) {
            throw new AdminException("Please provide a valid key!");
        }
    }
}
