package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusDao extends JpaRepository<Bus, Integer> {

    public List<Bus> findByBusType(String busType);
}
