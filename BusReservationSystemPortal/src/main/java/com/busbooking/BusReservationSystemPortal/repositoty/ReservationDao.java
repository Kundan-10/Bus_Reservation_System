package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDao extends JpaRepository<Reservation, Integer> {
}
