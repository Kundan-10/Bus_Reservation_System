package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.ReservationDTO;
import com.busbooking.BusReservationSystemPortal.Enum.Role;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.ReservationException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.*;
import com.busbooking.BusReservationSystemPortal.repositoty.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final UserDao userDao;
    private final BusDao busDao;
    private final ReservationDao reservationDao;

    @Override
    public Reservation addReservation(ReservationDTO reservationDTO) throws ReservationException, BusException, UserException {
        User loggedInUser = getAuthenticatedUser();
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));

        Optional<Bus> opt = busDao.findById(reservationDTO.getBusDTO().getBusId());

        Bus bus = opt.orElseThrow(() -> new ReservationException("Invalid bus details!"));

        if (reservationDTO.getJourneyDate().isBefore(LocalDate.now()))
            throw new ReservationException("Please enter future date!");

        if (!bus.getBusJourneyDate().isEqual(reservationDTO.getJourneyDate()))
            throw new ReservationException("Bus is not available on " + reservationDTO.getJourneyDate());

        if (!reservationDTO.getSource().equalsIgnoreCase(bus.getRouteFrom()) || !reservationDTO.getDestination().equalsIgnoreCase(bus.getRouteTo()))
            throw new ReservationException("Bus is not available on route : " + reservationDTO.getSource() + " - " + reservationDTO.getDestination());

        int seatsAvailable = bus.getAvailableSeats();
        if (seatsAvailable < reservationDTO.getNoOfSeatsToBook())
            throw new ReservationException("Reservation Failed! Available seats: " + seatsAvailable);

        bus.setAvailableSeats(seatsAvailable - reservationDTO.getNoOfSeatsToBook());

        Bus updatedBus = busDao.save(bus);

        Reservation reservation = new Reservation(
                updatedBus,
                "Successfully",
                LocalDate.now(),
                LocalTime.now(),
                bus.getRouteFrom(),
                bus.getRouteTo(),
                reservationDTO.getNoOfSeatsToBook(),
                bus.getFarePerSeat() * reservationDTO.getNoOfSeatsToBook(),
                reservationDTO.getJourneyDate(),
                user
        );
        user.getReservations().add(reservation);
        return reservationDao.save(reservation);

    }
    @Override
    public Reservation deleteReservation(Integer reservationId) throws ReservationException, BusException, UserException {
        User loggedInUser = getAuthenticatedUser();

        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));

        List<Reservation> reservationList = user.getReservations();

        for(int i=0; i<reservationList.size(); i++) {

            if(Objects.equals(reservationList.get(i).getReservationId(), reservationId))
            {
                Optional<Reservation> Opt = reservationDao.findById(reservationId);
                Reservation foundReservation = Opt.orElseThrow(()-> new ReservationException("No reservation found!"));
                Bus bus = foundReservation.getBus();

                if(foundReservation.getJourneyDate().isBefore(LocalDate.now())) throw new ReservationException("Cannot cancel! Journey completed.");

                bus.setAvailableSeats(bus.getAvailableSeats()+foundReservation.getNoOfSeatsBooked());
                busDao.save(bus);
                reservationList.remove(i);
                reservationDao.delete(foundReservation);
                return foundReservation;
            }
        }

        throw new UserException("Reservation Id:"+reservationId+" do not belong to the current user!");
    }

    @Override
    public Reservation viewReservation(Integer reservationId) throws ReservationException, AdminException, UserException {
        User loggedInUser = getAuthenticatedUser();
        validateAdmin(loggedInUser);

        Optional<Reservation> Opt = reservationDao.findById(reservationId);
        return Opt.orElseThrow(()-> new ReservationException("No reservation found!"));
    }

    @Override
    public List<Reservation> viewAllReservation() throws ReservationException, UserException {

        List<Reservation> reservationList = reservationDao.findAll();
        if(reservationList.isEmpty()) throw new ReservationException("No reservations found!");
        return reservationList;
    }

    @Override
    public List<Reservation> viewReservationByUser() throws ReservationException, UserException {
        User loggedInUser = getAuthenticatedUser();
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));
        return user.getReservations();
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
