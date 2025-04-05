package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.Enum.Role;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.Bus;
import com.busbooking.BusReservationSystemPortal.models.Route;
import com.busbooking.BusReservationSystemPortal.models.User;
import com.busbooking.BusReservationSystemPortal.repositoty.BusDao;
import com.busbooking.BusReservationSystemPortal.repositoty.RouteDao;
import com.busbooking.BusReservationSystemPortal.repositoty.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final RouteDao routeDao;
    private final BusDao busDao;
    private final UserDao userDao;

    @Override
    public Bus addBus(Bus bus) throws BusException, UserException {
       User authenticatedUser = getAuthenticatedUser();
       validateAdmin(authenticatedUser);
        Route route = routeDao.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
        if (Objects.nonNull(route)) {
            route.getBusList().add(bus);
            bus.setRoute(route);
            return busDao.save(bus);
        } else
            throw new BusException("Bus detail is not correct");
    }

    @Override
    public Bus updateBus(Bus bus) throws BusException, UserException {
        User authenticatedUser = getAuthenticatedUser();
        validateAdmin(authenticatedUser);

        Optional<Bus> existingBusOpt = busDao.findById(bus.getBusId());

        if (existingBusOpt.isPresent()) {

            Bus existingBus = existingBusOpt.get();

            if (!Objects.equals(existingBus.getAvailableSeats(), existingBus.getSeats()))
                throw new BusException("Cannot update already scheduled bus!");

            Route route = routeDao.findByRouteFromAndRouteTo(bus.getRouteFrom(), bus.getRouteTo());
            if (route == null) throw new BusException("Invalid route!");
            bus.setRoute(route);
            return busDao.save(bus);
        } else
            throw new BusException("Bus doesn't exist with busId : " + bus.getBusId());
    }

    @Override
    public Bus deleteBus(Integer busId) throws BusException, UserException {
        User authenticatedUser = getAuthenticatedUser();
        validateAdmin(authenticatedUser);
        Optional<Bus> bus = busDao.findById(busId);

        if (bus.isPresent()) {
            Bus existingBus = bus.get();
            if (LocalDate.now().isBefore(existingBus.getBusJourneyDate()) && !Objects.equals(existingBus.getAvailableSeats(), existingBus.getSeats()))
                throw new BusException("Cannot delete as the bus is scheduled and reservations are booked for the bus.");

            busDao.delete(existingBus);
            return existingBus;
        } else
            throw new BusException("Bus doesn't exist with busId : " + busId);
    }

    @Override
    public Bus viewBus(Integer busId) throws BusException {
        return busDao.findById(busId)
                .orElseThrow(() -> new BusException("Bus doesn't exist with busId : " + busId));
    }

    @Override
    public List<Bus> viewBusByType(String busType) throws BusException {
        return Optional.ofNullable(busDao.findByBusType(busType))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BusException("There is no bus of type " + busType));
    }

    @Override
    public List<Bus> viewAllBuses() throws BusException {
        List<Bus> busesList = busDao.findAll();

        return Optional.ofNullable(busesList)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BusException("There is no bus available now"));
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
            throw new UserException("Unauthorized! Only admin can Add Bus.");
        }
    }

}
