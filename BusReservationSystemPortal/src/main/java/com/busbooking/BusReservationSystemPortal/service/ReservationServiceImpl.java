package com.busbooking.BusReservationSystemPortal.service;

import com.busbooking.BusReservationSystemPortal.DTO.ReservationDTO;
import com.busbooking.BusReservationSystemPortal.exception.AdminException;
import com.busbooking.BusReservationSystemPortal.exception.BusException;
import com.busbooking.BusReservationSystemPortal.exception.ReservationException;
import com.busbooking.BusReservationSystemPortal.exception.UserException;
import com.busbooking.BusReservationSystemPortal.models.*;
import com.busbooking.BusReservationSystemPortal.repositoty.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final UserSessionDao userSessionDao;
    private final UserDao userDao;
    private final BusDao busDao;
    private final ReservationDao reservationDao;
    private final AdminSessionDao adminSessionDao;

    @Override
    public Reservation addReservation(ReservationDTO reservationDTO, String key) throws ReservationException, BusException, UserException {
        CurrentUserSession loggedInUser = validateUserSession(key);
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
    public Reservation deleteReservation(Integer reservationId, String key) throws ReservationException, BusException, UserException {
        CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);

        if(loggedInUser == null) {
            throw new UserException("Please provide a valid key to reserve seats!");
        }

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
                Bus updatedBus =busDao.save(bus);

                reservationList.remove(i);
                reservationDao.delete(foundReservation);
                return foundReservation;
            }
        }

        throw new UserException("Reservation Id:"+reservationId+" do not belong to the current user!");
    }

    @Override
    public Reservation viewReservation(Integer reservationId, String key) throws ReservationException, AdminException {
        CurrentAdminSession loggedInAdmin= adminSessionDao.findByUuid(key);

        if(loggedInAdmin == null) {
            throw new ReservationException("Please provide a valid key to view reservation!");
        }

        Optional<Reservation> Opt = reservationDao.findById(reservationId);
        return Opt.orElseThrow(()-> new ReservationException("No reservation found!"));
    }

    @Override
    public List<Reservation> viewAllReservation(String key) throws ReservationException, UserException {
         validateUserSession(key);

        List<Reservation> reservationList = reservationDao.findAll();
        if(reservationList.isEmpty()) throw new ReservationException("No reservations found!");
        return reservationList;
    }

    @Override
    public List<Reservation> viewReservationByUser(String key) throws ReservationException, UserException {

        CurrentUserSession loggedInUser= validateUserSession(key);
        User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found!"));
        return user.getReservations();
    }

    private CurrentUserSession validateUserSession(String key) throws UserException  {
        return Optional.ofNullable(userSessionDao.findByUuid(key))
                .orElseThrow(() -> new UserException("Invalid session key! Please provide a valid key!"));
    }
}
