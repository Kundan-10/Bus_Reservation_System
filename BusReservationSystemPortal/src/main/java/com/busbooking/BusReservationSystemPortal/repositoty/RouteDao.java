package com.busbooking.BusReservationSystemPortal.repositoty;

import com.busbooking.BusReservationSystemPortal.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteDao extends JpaRepository<Route, Integer> {

    public Route findByRouteFromAndRouteTo(String from,String to);
}
