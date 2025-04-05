package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.Enum.Role;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.RouteException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.models.Route;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.RouteDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteDao routeDao;
    private final UserDao userDao;

    @Override
    public Route addRoute(Route route) throws RouteException, AdminException, UserException {
        User authenticateUser =  getAuthenticatedUser();
        validateAdmin(authenticateUser);
        Optional<Route> existingRoute = Optional.ofNullable(routeDao.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo()));
        if (existingRoute.isPresent()) {
            throw new RouteException("Route from " + route.getRouteFrom() + " to " + route.getRouteTo() + " already exists!");
        }
        route.setBusList(new ArrayList<>());
        return routeDao.save(route);
    }

    @Override
    public Route updateRoute(Route route) throws RouteException, AdminException, UserException {
        User authenticateUser =  getAuthenticatedUser();
        validateAdmin(authenticateUser);

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
    public Route deleteRoute(int routeId) throws RouteException, AdminException, UserException {
        User authenticateUser =  getAuthenticatedUser();
        validateAdmin(authenticateUser);
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

    private User getAuthenticatedUser() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("Unauthorized request.");
        }

        String email = authentication.getName(); // Get logged-in user's email
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found!"));
    }

    private void validateAdmin(User authenticatedUser)throws UserException{
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new UserException("Access restricted: Admin privileges required.");
        }
    }
}
